package com.wk.crowd.service.impl;

import com.wk.crowd.mapper.MemberPOMapper;
import com.wk.crowd.pojo.po.MemberPO;
import com.wk.crowd.pojo.po.MemberPOExample;
import com.wk.crowd.service.MemberPOService;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)		//声明式事务
public class MemberPOServiceImpl implements MemberPOService {

	@Autowired
	private MemberPOMapper memberPOMapper;

	@Override
	public int getLoginAcct(String loginAcct) {
		MemberPOExample example = new MemberPOExample();
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		return memberPOMapper.countByExample(example);
	}

	@Override
	//设置非只读、传播方式、回滚条件
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveMemberPO(MemberPO memberPO) {
		memberPOMapper.insertSelective(memberPO);
	}

	@Override
	public MemberPO getMemberByAcct(String loginAcct) {
		MemberPOExample example = new MemberPOExample();
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		List<MemberPO> list = memberPOMapper.selectByExample(example);
		if(CrowdUtils.collectionEffectiveCheck(list)){
			return list.get(0);
		}
		return null;
	}
}
