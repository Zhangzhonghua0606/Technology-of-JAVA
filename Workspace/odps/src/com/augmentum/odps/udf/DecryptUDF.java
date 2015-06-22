package com.augmentum.odps.udf;

import com.aliyun.odps.udf.UDF;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class DecryptUDF extends UDF {

	public String evaluate(String encryptedText) {
		String result = null;
		if (encryptedText != null) {
			String appId = "wxed8ad9f7aa3a8a4a";
			String token = "XF3E2FF34DFD4457FASAF34565FDA3561";
			String encodingAesKey = "1QW34RDFB567UI34DGT60OWSMFJKE432WASXLPO0I7t";
			
			WXBizMsgCrypt pc = null;
			try {
				pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
				result = pc.decrypt(encryptedText.trim());
			} catch (AesException e) {
				return e.getMessage();
			}
		}
		return result;
	}
}

