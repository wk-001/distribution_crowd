package com.wk.crowd.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CodeTest {

	@Test
	public void jsonTest(){
		List<String> list = new ArrayList<>();
		list.add("1111111111");
		list.add("2222222222");
		list.add("3333333333");
		list.add("4444444444");
		String str = JSON.toJSONString(list);
		System.out.println("str = " + str);
	}

}
