package com.wk.crowd.api;

import com.wk.crowd.pojo.MemberPO;
import com.wk.crowd.pojo.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {

	@RequestMapping("/retrieve/login/acct/count")
	ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginAcct") String loginAcct);

	/**
	 * @RequestBody：将传入的json字符串转换为对象
	 * @param memberPO
	 * @return
	 */
	@RequestMapping("/save/member/remote")
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
}