package com.wk.crowd.controller;

import com.wk.crowd.service.MemberPOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController			//服务端返回的都是json数据
public class MemberController {

	@Autowired
	private MemberPOService memberPOService;
}
