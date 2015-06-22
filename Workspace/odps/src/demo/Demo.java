package demo;

import com.augmentum.odps.udf.DecryptUDF;
import com.augmentum.odps.udf.XMLParse;

public class Demo {
	
	public static void main(String[] args) throws Exception {
		String body = "<xml><ToUserName><![CDATA[gh_3a3787c421e9]]></ToUserName><Encrypt><![CDATA[sAIwJZVm2SsTFY+W2BeJodigKM8CLM9zRETQYckE+GAr86B/IWINWFfPGPj2r5mpqHjtdxcKogvQbKx9v7otN3MT7Dxh9RatI6RN2XdZtKVzbJIAgUY4VHv9GdVL5xKqjuy1zige+wWkdhIekptLHcxKZ3gLiSMdu4r61pGRaKMhvrNmskdv2309oyJGhdAEbYSUef63OVL2STPPsCgWmx2dFWCDiPMoxKxUTkz/Kv9sv8pQ/1+olT1Ijnf06Pz+uVxkttqhRW12+qUmAzdeZUTxQih5Peq29mAXnok+cK8ER6Q4asD6qcdviVSpys9hVE8tC5vOJMu1xq82qaO27w1LwEx2P+2JFMICATqNnwdM5auKkIxKtC+AL3UEdoyfoEHCUg0AX69nK0Cl+3AsMJvFN7BDQ6imc2rk0RPaEQQ/zDljsW61OJjtI9eew5TBJvP0pSuSIflWiknnBlSfKNelzQ9+4oNPw+kZAt3iuSbKgXmtmuVlEbx9cjBcVp1l]]></Encrypt></xml>";
		
		String result = new XMLParse().evaluate(body, "Encrypt");
		String unencrypt = new DecryptUDF().evaluate(result);
		
		System.out.println(unencrypt);
	} 
}
