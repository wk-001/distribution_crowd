package com.wk.crowd.service;

import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.po.MemberPO;

public interface MemberPOService {

	int getLoginAcct(String loginacct);

	void saveMemberPO(MemberPO memberPO);

	MemberPO getMemberByAcct(String loginacct);

	MemberLaunchInfoPO getLaunchInfoByMemberId(String memberid);
}