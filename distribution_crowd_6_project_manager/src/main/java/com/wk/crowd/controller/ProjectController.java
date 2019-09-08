package com.wk.crowd.controller;

import com.alibaba.fastjson.JSON;
import com.wk.crowd.api.DataBaseOperationRemoteService;
import com.wk.crowd.api.RedisOperationRemoteService;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.vo.MemberConfirmInfoVO;
import com.wk.crowd.pojo.vo.ProjectVO;
import com.wk.crowd.pojo.vo.ReturnVO;
import com.wk.crowd.pojo.vo.TokenVO;
import com.wk.crowd.util.CrowdConstant;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {

	@Autowired
	private RedisOperationRemoteService redisService;

	@Autowired
	private DataBaseOperationRemoteService dataBaseService;

	/**
	 * 将完整的项目信息从Redis中取出保存到数据库中
	 * @param tokenVO
	 * @return
	 */
	@RequestMapping("project/manager/save/hole/project")
	public ResultEntity<String> saveWholeProject(@RequestBody TokenVO tokenVO){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		String memberSignToken = tokenVO.getMemberSignToken();
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (memberId == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		//取出Redis中临时的完整项目信息保存到数据库
		ProjectVO projectVOByRedis = getProjectVO(tokenVO).getData();
		ResultEntity<String> resultEntity1 = dataBaseService.saveProjectRemote(projectVOByRedis, memberId);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return ResultEntity.failed(resultEntity1.getMessage());
		}
		//保存完成之后删除Redis中的临时数据
		String projectTempToken = tokenVO.getProjectTempToken();
		return redisService.removeByKey(projectTempToken);
	}

	//工具类;检查用户令牌是否登录、获取项目令牌，从Redis中取出projectVO对象
	public ResultEntity<ProjectVO> getProjectVO(TokenVO tokenVO){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		String memberSignToken = tokenVO.getMemberSignToken();
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}

		//从前台传入的对象中获取project令牌
		String projectToken = tokenVO.getProjectTempToken();

		//redis中查询projectVo对象
		ResultEntity<String> resultEntity1 = redisService.retrieveStringValueByStringKey(projectToken);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return ResultEntity.failed(resultEntity1.getMessage());
		}

		//从Redis中查询到对象字符串
		String projectVoJson = resultEntity1.getData();

		//json转对象
		ProjectVO projectVOByRedis = JSON.parseObject(projectVoJson, ProjectVO.class);
		return ResultEntity.successWithData(projectVOByRedis);
	}

	//将易付宝账号和身份证号存入项目对象
	@RequestMapping("project/manager/save/confirm/info")
	public ResultEntity<String> saveConfirmInfo(@RequestBody MemberConfirmInfoVO memberConfirmInfoVO){
		ProjectVO projectVOByRedis = getProjectVO(memberConfirmInfoVO).getData();
		String projectToken = projectVOByRedis.getProjectTempToken();
		//将易付宝账号和身份证号存入项目对象
		projectVOByRedis.setMemberConfirmInfoVO(memberConfirmInfoVO);
		//将保存好回报信息的项目对象序列化存入redis
		String projectVoStr = JSON.toJSONString(projectVOByRedis);
		return redisService.saveNormalStringKeyValue(projectToken, projectVoStr, -1);
	}


	/**
	 * 保存回报信息
	 */
	@RequestMapping("project/manager/save/return/info")
	public ResultEntity<String> saveReturnInfo(@RequestBody ReturnVO returnVO){

		ProjectVO projectVOByRedis = getProjectVO(returnVO).getData();
		String projectToken = projectVOByRedis.getProjectTempToken();

		//获取旧的回报信息集合
		List<ReturnVO> returnVOList = projectVOByRedis.getReturnVOList();
		//判断returnVOList中是否有数据
		if(!CrowdUtils.collectionEffectiveCheck(returnVOList)){
			returnVOList = new ArrayList<>();
			projectVOByRedis.setReturnVOList(returnVOList);
		}

		//将当前回报信息存入list
		returnVOList.add(returnVO);

		//将保存好回报信息的项目对象序列化存入redis
		String projectVoStr = JSON.toJSONString(projectVOByRedis);
		return redisService.saveNormalStringKeyValue(projectToken, projectVoStr, -1);
	}

	/**
	 * 保存项目信息
	 */
	@RequestMapping("project/manager/save/project/info")
	public ResultEntity<String> saveProjectInfo(@RequestBody ProjectVO projectVO){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		String memberSignToken = projectVO.getMemberSignToken();
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}

		/*//从前台传入的对象中获取project令牌
		String projectToken = projectVO.getProjectTempToken();

		//redis中查询projectVo对象
		ResultEntity<String> resultEntity1 = redisService.retrieveStringValueByStringKey(projectToken);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return ResultEntity.failed(resultEntity1.getMessage());
		}

		//从Redis中查询到对象字符串
		String projectVoJson = resultEntity1.getData();

		//json转对象
		ProjectVO projectVOByRedis = JSON.parseObject(projectVoJson, ProjectVO.class);

		//BeanUtils.copyProperties会把null也复制到新对象，所以要把Redis中对象的值设置到新对象中
		projectVO.setHeaderPicturePath(projectVOByRedis.getHeaderPicturePath());
		projectVO.setDetailPicturePathList(projectVOByRedis.getDetailPicturePathList());
		//将前台传过来带有数据的对象复制到从Redis中取出来带有图片信息的对象中,Redis包含所有的数据*/

		/*从Redis中取出项目初始化的内容
		ProjectVO projectVOByRedis = getProjectVO(projectVO).getData();
		BeanUtils.copyProperties(projectVO,projectVOByRedis);*/

		//将存入大图路径的对象重新转成json存入redis
		String projectToken = projectVO.getProjectTempToken();
		String projectVoStr = JSON.toJSONString(projectVO);
		return redisService.saveNormalStringKeyValue(projectToken, projectVoStr, -1);
	}

	/**
	 * 详细信息图片地址
	 * @param memberSignToken	用户登录令牌
	 * @param projectToken		项目令牌
	 * @param detailPicturePathList		详细信息图片存储在oss的路径
	 * @return
	 *
	@RequestMapping("save/detail/picture/list/path")
	public ResultEntity<String> saveDetailPictureListPath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectToken") String projectToken,
			@RequestParam("detailPicturePathList") List<String> detailPicturePathList
	){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}

		//redis中查询projectVo对象
		ResultEntity<String> resultEntity1 = redisService.retrieveStringValueByStringKey(projectToken);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return ResultEntity.failed(resultEntity1.getMessage());
		}

		//从Redis中查询到对象字符串
		String projectVoJson = resultEntity1.getData();

		//json转对象
		ProjectVO projectVO = JSON.parseObject(projectVoJson, ProjectVO.class);

		//将大图的路径存入Redis
		projectVO.setDetailPicturePathList(detailPicturePathList);

		//将存入大图路径的对象重新转成json存入redis
		String projectVoStr = JSON.toJSONString(projectVO);
		return redisService.saveNormalStringKeyValue(projectToken, projectVoStr, -1);
	}*/

	/**
	 * 保存大图地址
	 *  memberSignToken		用户登录令牌
	 *  projectToken 		项目令牌
	 *  headPicPath 	 	头图存储在oss的路径
	 * @return

	@RequestMapping("save/head/picture/path")
	public ResultEntity<String> saveHeadPicturePath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectToken") String projectToken,
			@RequestParam("headPicPath") String headPicPath
	){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}

		//redis中查询projectVo对象
		ResultEntity<String> resultEntity1 = redisService.retrieveStringValueByStringKey(projectToken);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return ResultEntity.failed(resultEntity1.getMessage());
		}

		//从Redis中查询到对象字符串
		String projectVoJson = resultEntity1.getData();

		//json转对象
		ProjectVO projectVO = JSON.parseObject(projectVoJson, ProjectVO.class);

		//将大图的路径存入Redis
		projectVO.setHeaderPicturePath(headPicPath);

		//将存入大图路径的对象重新转成json存入redis
		String projectVoStr = JSON.toJSONString(projectVO);
		return redisService.saveNormalStringKeyValue(projectToken, projectVoStr, -1);
	}*/


	/*项目创建后再Redis中初始化，创建临时对象存放项目信息*/
	@RequestMapping("project/manager/initCreation")
	public ResultEntity<ProjectVO> initCreation(@RequestParam("memberSignToken")String memberSignToken){
		//检查是否登录(Redis中memberSignToken对应的key是否有值)
		ResultEntity<String> resultEntity = redisService.retrieveStringValueByStringKey(memberSignToken);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}
		String memberId = resultEntity.getData();
		if (memberId == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		//创建项目对象存放令牌信息
		ProjectVO projectVO = new ProjectVO();
		//用户登录令牌
		projectVO.setMemberSignToken(memberSignToken);
		//项目令牌
		String projectToken = CrowdUtils.generateRedisKey(CrowdConstant.REDIS_PROJECT_TEMP_TOKEN_PREFIX);
		projectVO.setProjectTempToken(projectToken);
		//对象转json
		String jsonString = JSON.toJSONString(projectVO);

		//将项目令牌作为key，项目对象json作为value存入Redis，项目信息在创建完成后删除，所以不设置过期时间
		redisService.saveNormalStringKeyValue(projectToken,jsonString,-1);
		//初始化完成后的数据在Redis中存一份，并返回给调用方一份
		return ResultEntity.successWithData(projectVO);
	}

}
