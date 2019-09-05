package com.wk.crowd.service.impl;

import com.wk.crowd.mapper.*;
import com.wk.crowd.pojo.po.MemberConfirmInfoPO;
import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.po.ProjectPO;
import com.wk.crowd.pojo.po.ReturnPO;
import com.wk.crowd.pojo.vo.MemberConfirmInfoVO;
import com.wk.crowd.pojo.vo.MemberLaunchInfoVO;
import com.wk.crowd.pojo.vo.ProjectVO;
import com.wk.crowd.pojo.vo.ReturnVO;
import com.wk.crowd.service.ProjectService;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

	@Autowired
	MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

	@Autowired

	ProjectItemPicPOMapper projectItemPicPOMapper;

	@Autowired
	ProjectPOMapper projectPOMapper;

	@Autowired
	ReturnPOMapper returnPOMapper;

	@Autowired
	TagPOMapper tagPOMapper;

	@Autowired
	TypePOMapper typePOMapper;

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveProject(ProjectVO projectVO,String memberId) {
		//1.保存ProjectPO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO,projectPO);
		projectPO.setMemberid(Integer.parseInt(memberId));
		//mapper文件insert方法需要设置"useGeneratedKeys="true" keyProperty="主键属性名""
		projectPOMapper.insertSelective(projectPO);
		//2.获取保存ProjectPO后的自增主键
		Integer projectPOId = projectPO.getId();
		//3.保存typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		if(CrowdUtils.collectionEffectiveCheck(typeIdList)){
			typePOMapper.insertRelationBatch(projectPOId,typeIdList);
		}
		//4.保存tagIdList
		List<Integer> tagIdList = projectVO.getTagIdList();
		if(CrowdUtils.collectionEffectiveCheck(tagIdList)){
			tagPOMapper.insertRelationBatch(projectPOId,tagIdList);
		}
		//5.保存detailPicturePathList
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		if(CrowdUtils.collectionEffectiveCheck(detailPicturePathList)){
			projectItemPicPOMapper.insertBatch(projectPOId,detailPicturePathList);
		}
		//6.保存MemberLaunchInfoPO
		MemberLaunchInfoVO memberLaunchInfoVO = projectVO.getMemberLaunchInfoVO();
		if (memberLaunchInfoVO != null) {
			MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
			BeanUtils.copyProperties(memberLaunchInfoVO,memberLaunchInfoPO);
			memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));
			memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);
		}
		//7.根据ReturnVO的List保存ReturnPO
		List<ReturnVO> returnVOList = projectVO.getReturnVOList();
		if(CrowdUtils.collectionEffectiveCheck(returnVOList)){
			List<ReturnPO> returnPOList = new ArrayList<>();
			for (ReturnVO returnVO : returnVOList) {
				ReturnPO returnPO = new ReturnPO();
				BeanUtils.copyProperties(returnVO,returnPO);
				returnPO.setProjectid(projectPOId);
				returnPOList.add(returnPO);
			}
			returnPOMapper.insertBatch(returnPOList);
		}
		//8.保存MemberConfirmInfoPO
		MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
		if (memberConfirmInfoVO != null) {
			MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO(null,
					Integer.parseInt(memberId),
					memberConfirmInfoVO.getPaynum(),
					memberConfirmInfoVO.getCardnum());
			memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
		}
	}
}
