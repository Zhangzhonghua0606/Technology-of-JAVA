package com.augmentum.odps.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Instance.TaskStatus;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.task.SQLTask;
import com.augmentum.odps.udf.XMLParse;
import com.augmentum.odps.utli.CommonUtils;
import com.augmentum.odps.utli.XMLUtils;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class LogDetail {

    public static final String TOUSERNAME = "ToUserName";
    public static final String FROMUSERNAME = "FromUserName";
    public static final String CREATETIME = "CreateTime";
    public static final String MSGTYPE = "MsgType";
    public static final String EVENT = "Event";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String PRECISION = "Precision";
    public static final String EVENTKEY = "EnevtKey";
    public static final String TICKET = "Ticket";
    public static final String CONTENT = "Content";
    public static final String MEDIAID = "MediaId";
    public static final String FORMAT = "Format";
    public static final String THUMBMEDIAID = "ThumbMediaId";
    public static final String LOCATIONX = "Location_X";
    public static final String LOCATIONY = "Location_Y";
    public static final String SCALE = "Scale";
    public static final String LABEL = "Label";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String URL = "Url";
    public static final String MSGID = "MsgId";
    public static final String STATUS = "Satus";
    public static final String TOTALCOUNT = "TotalCount";
    public static final String FILTERCOUNT = "FilterCount";
    public static final String SENTCOUNT = "SentCount";
    public static final String ERRORCOUNT = "ErrorCount";

    private Account account;
    private Odps odps;

    public LogDetail() {
        account = new AliyunAccount("swsWVDPsVdyASRSo", "lupLCzAmWKmQ4q4JRxafKkNrEsPI2C");
        odps = new Odps(account);
        String odpsUrl = "http://service.odps.aliyun.com/api";
        odps.setEndpoint(odpsUrl);
        odps.setDefaultProject("we_connect");
    }

    // Executes ODPS SQL.
    void excute(String sql) throws OdpsException {
        Instance instance = SQLTask.run(odps, sql);
        instance.waitForSuccess();
    }

    // Gets data from ODPS repository.
    private String getData(String sql) throws OdpsException {
        Instance instance = SQLTask.run(odps, sql);
        instance.waitForSuccess();

        String result = "";
        Map<String, String> results = instance.getTaskResults();
        Map<String, TaskStatus> taskStatus = instance.getTaskStatus();

        for (Entry<String, TaskStatus> status : taskStatus.entrySet()) {
            result = results.get(status.getKey());
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        String appId = "wxed8ad9f7aa3a8a4a";
        String token = "XF3E2FF34DFD4457FASAF34565FDA3561";
        String encodingAesKey = "1QW34RDFB567UI34DGT60OWSMFJKE432WASXLPO0I7t";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse(args[0]);
        Date endDate = sdf.parse(args[1]);

        LogDetail logDetail = new LogDetail();

        // 1.Creates a temporary table 'tmp' to store json-parsed and xml
        // data(drop table 'tmp' if exists).
        // 2.Inserts data into table 'tmp' according to table 'wx_accesslogs'.
        // 3.Gets log's count.
        String countSql = "select count(1) from tmp where sls_time >= " + CommonUtils.convertToUnixTime(beginDate)
                + " and sls_time < " + CommonUtils.convertToUnixTime(CommonUtils.addDays(endDate, 1)) + ";";

        String countResult = logDetail.getData(countSql);
        int totalCount = Integer.parseInt(countResult.split("\n")[1].replace("\"", "").trim());
        int pageSize = 1000;
        int beginIndex = 0;
        int endIndex = 0;

        while (beginIndex < totalCount) {
            endIndex = (beginIndex + pageSize) >= totalCount ? totalCount : (beginIndex + pageSize);

            // 4.Gets xml data from table 'tmp' according to rank.
            String sql = "select body from (select body,row_number() over (partition by sls_topic order by sls_time desc,sls_source) as rank from tmp) sub "
                    + "where rank > " + beginIndex + " and rank <= " + endIndex + ";";

            String result = logDetail.getData(sql);

            // 5.Parses xml data on local.
            String[] info = result.split("\n");
            if (info.length > 1) {
                for (int i = 1; i < info.length; i++) {
                    Map<String, Object> data = new HashMap<String, Object>();
                    String text = info[i].replace("\"", "").trim();

                    if (text.startsWith("<xml>")) {
                        text = text.replace("\\\\x0A", "");
                        String encryptedText = new XMLParse().evaluate(text, "Encrypt");

                        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);

                        String unencryptedXML = pc.decrypt(encryptedText);
                        data = XMLUtils.parseXMLByDom4j(unencryptedXML);
                    }
                    // 6.Inserts data into table 'wx_accesslogs_detail' one by
                    // one when finishes parsing.
                    String insertSql = "";
                    if (data.size() > 0) {
                        insertSql = "insert into table wx_accesslogs_detail partition(sls_partition_time)"
                                + "select sls_source,sls_time,sls_topic,proxy_ip,time,http_method,http_path,http_code,rsp_size,rsp_time,proxy_rsp_time,client_ip,'"
                                + data.get(TOUSERNAME)
                                + "','"
                                + data.get(FROMUSERNAME)
                                + "',"
                                + data.get(CREATETIME)
                                + ",'"
                                + data.get(MSGTYPE)
                                + "','"
                                + data.get(EVENT)
                                + "',"
                                + data.get(LATITUDE)
                                + ","
                                + data.get(LONGITUDE)
                                + ","
                                + data.get(PRECISION)
                                + ",'"
                                + data.get(EVENTKEY)
                                + "','"
                                + data.get(TICKET)
                                + "','"
                                + data.get(CONTENT)
                                + "',"
                                + data.get(MEDIAID)
                                + ",'"
                                + data.get(FORMAT)
                                + "',"
                                + data.get(THUMBMEDIAID)
                                + ","
                                + data.get(LOCATIONX)
                                + ","
                                + data.get(LOCATIONY)
                                + ","
                                + data.get(SCALE)
                                + ",'"
                                + data.get(LABEL)
                                + "','"
                                + data.get(TITLE)
                                + "','"
                                + data.get(DESCRIPTION)
                                + "','"
                                + data.get(URL)
                                + "',"
                                + data.get(MSGID)
                                + ",'"
                                + data.get(STATUS)
                                + "',"
                                + data.get(TOTALCOUNT)
                                + ","
                                + data.get(FILTERCOUNT)
                                + ","
                                + data.get(SENTCOUNT)
                                + ","
                                + data.get(ERRORCOUNT)
                                + ",sls_partition_time from "
                                + "(select *,row_number() over (partition by sls_topic order by sls_time desc,sls_source) as rank from tmp where sls_time >= "
                                + CommonUtils.convertToUnixTime(beginDate)
                                + " and sls_time < "
                                + CommonUtils.convertToUnixTime(CommonUtils.addDays(endDate, 1))
                                + ") sub "
                                + "where rank = " + (beginIndex + i) + ";";

                    } else {
                        insertSql = "insert into table wx_accesslogs_detail partition(sls_partition_time) "
                                + "select sls_source,sls_time,sls_topic,proxy_ip,time,http_method,http_path,http_code,rsp_size,rsp_time,proxy_rsp_time,client_ip,"
                                + "null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,"
                                + "sls_partition_time from (select *,row_number() over (partition by sls_topic order by sls_time desc,sls_source) as rank from tmp where sls_time >= "
                                + CommonUtils.convertToUnixTime(beginDate) + " and sls_time < "
                                + CommonUtils.convertToUnixTime(CommonUtils.addDays(endDate, 1)) + ") sub "
                                + "where rank = " + (beginIndex + i) + ";";
                    }
                    logDetail.excute(insertSql);
                    System.out.println(beginIndex + i + "----Done!");
                }
            }
            beginIndex = endIndex;
        }
    }
}
