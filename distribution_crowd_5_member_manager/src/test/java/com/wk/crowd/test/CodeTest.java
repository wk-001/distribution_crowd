package com.wk.crowd.test;

import com.wk.crowd.util.CrowdUtils;
import org.junit.Test;

public class CodeTest {

	@Test
	public void testShortMessage(){
		String appcode = "52af38ee6a48458bb3e140682716e79b";
		String randomCode = CrowdUtils.randomCode(4);
		String phoneNum = "18515411913";
		CrowdUtils.sendMessage(appcode,randomCode,phoneNum);
	}

}
