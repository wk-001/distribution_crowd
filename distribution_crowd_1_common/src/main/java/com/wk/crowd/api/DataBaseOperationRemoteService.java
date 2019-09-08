package com.wk.crowd.api;

import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.po.MemberPO;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("database-provider")	//调用注册到eureka中的微服务名称
public interface DataBaseOperationRemoteService {

	//根据memberid获取项目发起人信息
	@RequestMapping("get/member/launch/info/po")
	ResultEntity<MemberLaunchInfoPO> getLaunchInfoByMemberId(@RequestParam("memberid") String memberid);

	/**
	 * 查询账户个数
	 * @return
	 */
	@RequestMapping("/retrieve/login/acct/count")
	ResultEntity<Integer> retrieveLoginAcctCount(@RequestParam("loginacct") String loginacct);

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
	 * @return
	 */
	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginacct") String loginacct);

	@RequestMapping("save/project/remote/{memberId}")
	ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO, @PathVariable("memberId")String memberId);
}