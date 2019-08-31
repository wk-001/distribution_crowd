package com.wk.crowd.controller;

import com.wk.crowd.pojo.MemberPO;
import com.wk.crowd.pojo.ResultEntity;
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

	@RequestMapping("/retrieve/login/acct/count")
	public ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginAcct") String loginAcct){
		if(!CrowdUtils.strEffectiveCheck(loginAcct)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGINACCT_INVALID);
		}
		try {
			int count = memberPOService.getLoginAcct(loginAcct);
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
}
