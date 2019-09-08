package com.wk.crowd.api;

import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.MemberConfirmInfoVO;
import com.wk.crowd.pojo.vo.ProjectVO;
import com.wk.crowd.pojo.vo.ReturnVO;
import com.wk.crowd.pojo.vo.TokenVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("project-manager")
public interface ProjectOperationRemoteService {

	/*项目创建后再Redis中初始化，创建临时对象存放项目信息*/
	@RequestMapping("project/manager/initCreation")
	public ResultEntity<ProjectVO> initCreation(@RequestParam("memberSignToken")String memberSignToken);

	//保存项目信息
	@RequestMapping("project/manager/save/project/info")
	public ResultEntity<String> saveProjectInfo(@RequestBody ProjectVO projectVO);

	//保存回报信息
	@RequestMapping("project/manager/save/return/info")
	public ResultEntity<String> saveReturnInfo(@RequestBody ReturnVO returnVO);

	//将易付宝账号和身份证号存入项目对象
	@RequestMapping("project/manager/save/confirm/info")
	public ResultEntity<String> saveConfirmInfo(@RequestBody MemberConfirmInfoVO memberConfirmInfoVO);

	//将完整的项目信息从Redis中取出保存到数据库中
	@RequestMapping("project/manager/save/hole/project")
	public ResultEntity<String> saveWholeProject(@RequestBody TokenVO tokenVO);

	/**
	 * 详细信息图片地址
	 * @param memberSignToken	用户登录令牌
	 * @param projectToken		项目令牌
	 * @param detailPicturePathList		详细信息图片存储在oss的路径
	 * @return
	 */
	@RequestMapping("save/detail/picture/list/path")
	public ResultEntity<String> saveDetailPictureListPath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectToken") String projectToken,
			@RequestParam("detailPicturePathList") List<String> detailPicturePathList
	);

	/**
	 * 保存大图地址
	 *  memberSignToken		用户登录令牌
	 *  projectToken 		项目令牌
	 *  headPicPath 	 	头图存储在oss的路径
	 * @return
	 */
	@RequestMapping("save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectToken") String projectToken,
			@RequestParam("headPicPath") String headPicPath
	);

}