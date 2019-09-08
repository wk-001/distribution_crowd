package com.wk.crowd.controller;

import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.po.MemberPO;
import com.wk.crowd.service.MemberPOService;
import com.wk.crowd.util.CrowdConstant;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController			//服务端返回的都是json数据
public class MemberController {

	@Autowired
	private MemberPOService memberPOService;

	//根据memberid获取项目发起人信息
	@RequestMapping("get/member/launch/info/po")
	public ResultEntity<MemberLaunchInfoPO> getLaunchInfoByMemberId(@RequestParam("memberid") String memberid){
		MemberLaunchInfoPO memberLaunchInfoPO = memberPOService.getLaunchInfoByMemberId(memberid);
		return ResultEntity.successWithData(memberLaunchInfoPO);
	}

	@RequestMapping("/retrieve/login/acct/count")
	public ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginacct") String loginacct){
		if(!CrowdUtils.strEffectiveCheck(loginacct)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGINACCT_INVALID);
		}
		try {
			int count = memberPOService.getLoginAcct(loginacct);
			return ResultEntity.successWithData(count);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

	@RequestMapping("/save/member/remote")
	public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO){
		try {
			memberPOService.saveMemberPO(memberPO);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

	@RequestMapping("/retrieve/member/by/login/acct")
	public ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginacct") String loginacct){
		try {
			MemberPO memberPO = memberPOService.getMemberByAcct(loginacct);
			return ResultEntity.successWithData(memberPO);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}
}
