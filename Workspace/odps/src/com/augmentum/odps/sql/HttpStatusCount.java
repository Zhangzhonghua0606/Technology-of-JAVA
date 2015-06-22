package com.augmentum.odps.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Instance.TaskStatus;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.task.SQLTask;
import com.augmentum.odps.utli.CommonUtils;

public class HttpStatusCount {
	private Account account;
	private Odps odps;

	public HttpStatusCount() {
		account = new AliyunAccount("swsWVDPsVdyASRSo","lupLCzAmWKmQ4q4JRxafKkNrEsPI2C");
		odps = new Odps(account);
		String odpsUrl = "http://service.odps.aliyun.com/api";
		odps.setEndpoint(odpsUrl);
		odps.setDefaultProject("we_connect");
	}

	// Executes ODPS SQL.
	private void excute(String sql) throws OdpsException {
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
		HttpStatusCount httpStatusCount = new HttpStatusCount();

		String resetSql = "drop table if exists wx_accesslogs_http_code_result;";
		String createSql = "create table wx_accesslogs_http_code_result(operated_date string,http_code bigint,count bigint);";
        
		// Resets ODPS table.
		httpStatusCount.excute(resetSql);
		httpStatusCount.excute(createSql);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = sdf.parse(args[0]);
		Date endDate = sdf.parse(args[1]);
		Date currentDate = beginDate;

		while (currentDate.getTime() <= endDate.getTime()) {
			String date = sdf.format(currentDate);

			String sql = "insert into table wx_accesslogs_http_code_result "
					+ "select '" + date + "' as operated_date, http_code, count(http_code) as count from "
					+ "(select get_json_object(sls_extract_others, '$.http_code') as http_code, sls_time from wx_accesslogs where sls_time >= "
					+ CommonUtils.convertToUnixTime(currentDate) + " and sls_time < "
					+ CommonUtils.convertToUnixTime(CommonUtils.addDays(currentDate, 1))
					+ ") temp group by http_code;";

			httpStatusCount.excute(sql);

			currentDate = CommonUtils.addDays(currentDate, 1);
		}
		
		String querySql = "select * from wx_accesslogs_http_code_result;";
		String result = httpStatusCount.getData(querySql);

		System.out.println(result);
	}
}
