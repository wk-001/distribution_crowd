package com.wk.crowd.api;

import com.wk.crowd.pojo.po.MemberPO;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {

	/**
	 * 查询账户个数
	 * @param loginAcct
	 * @return
	 */
	@RequestMapping("/retrieve/login/acct/count")
	ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginAcct") String loginAcct);

	/**
	 * 保存用户信息
	 * @RequestBody：将传入的json字符串转换为对象
	 * @param memberPO
	 * @return
	 */
	@RequestMapping("/save/member/remote")
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);

	/**
	 * 登录
	 * @param loginAcct
	 * @return
	 */
	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);

	@RequestMapping("save/project/remote/{memberId}")
	ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO, @PathVariable("memberId")String memberId);
}