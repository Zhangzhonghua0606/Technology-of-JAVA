package com.augmentum.odps.dom4j;

import java.util.Map;
import java.util.Map.Entry;

import com.aliyun.odps.Instance;
import com.aliyun.odps.Instance.TaskStatus;
import com.aliyun.odps.Odps;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.task.SQLTask;
import com.augmentum.odps.utli.XMLUtils;

public class Dom4jDemo {

	
	
	public static void main(String[] args) throws Exception {
		AliyunAccount account = new AliyunAccount("swsWVDPsVdyASRSo","lupLCzAmWKmQ4q4JRxafKkNrEsPI2C");
		Odps odps = new Odps(account);
		String odpsUrl = "http://service.odps.aliyun.com/api";
		odps.setEndpoint(odpsUrl);
		odps.setDefaultProject("we_connect");
		
		String sql = "select get_json_object(sls_extract_others, '$.body') from wx_accesslogs limit 3;";
		Instance instance = SQLTask.run(odps, sql);
		instance.waitForSuccess();
		
		Map<String, String> results = instance.getTaskResults();
	    Map<String, TaskStatus> taskStatus = instance.getTaskStatus();

	    String result = "";
	    for (Entry<String, TaskStatus> status : taskStatus.entrySet()) {
	        result = results.get(status.getKey());
	    }
	    
	    String[] info = result.split("\n");
	    if(info.length > 1){
            for (int i = 1; i < info.length; i++) {
            	String text = info[i].replace("\"", "").trim();
            	text = text.replace("\\\\x0A", "");
            	XMLUtils.parseXMLByDom4j(text);
            	System.out.println();
            }
	    }
	}
}
