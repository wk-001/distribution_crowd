package com.wk.crowd.service.impl;

import com.wk.crowd.mapper.MemberPOMapper;
import com.wk.crowd.service.MemberPOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional		//声明式事务
public class MemberPOServiceImpl implements MemberPOService {

	@Autowired
	private MemberPOMapper memberPOMapper;

}
