package com.wk.crowd.api;

import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.vo.MemberSignSuccessVO;
import com.wk.crowd.pojo.vo.MemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("member-manager")	//调用注册到eureka中的微服务名称
public interface MemberManagerRemoteService {

	@RequestMapping("get/Member/Launch/InfoPO/By/Token")
	public ResultEntity<MemberLaunchInfoPO> getMemberLaunchInfoPOByToken(@RequestParam("token") String token);

	@RequestMapping("member/manager/register")
	public ResultEntity<String> register(@RequestBody() MemberVO memberVO);

	@RequestMapping("member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginacct") String loginacct,
			@RequestParam("userpswd") String userpswd);

	@RequestMapping("member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token);

	@RequestMapping("member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum")String phoneNum);
}
