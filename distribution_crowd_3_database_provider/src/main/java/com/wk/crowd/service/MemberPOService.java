package com.wk.crowd.service;

import com.wk.crowd.pojo.MemberPO;

public interface MemberPOService {

	int getLoginAcct(String loginAcct);

	void saveMemberPO(MemberPO memberPO);
}