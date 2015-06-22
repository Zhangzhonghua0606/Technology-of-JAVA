package com.augmentum.odps.udf;

import com.aliyun.odps.udf.UDF;

public final class StringFormatUDF extends UDF {
	
	public String evaluate(String str) {
		String result = null;
		
	    if (str == null || str.equals("")) 
	    { 
	    	return result; 
	    }
	    
	    try {
	    	result = str.substring(9, str.length() - 3);
	    } catch (Exception e) {
	    	return result;
	    }
	    
	    return result;
	  }
}
