package com.wk.crowd.controller;

import com.wk.crowd.api.DataBaseOperationRemoteService;
import com.wk.crowd.api.RedisOperationRemoteService;
import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.pojo.po.MemberLaunchInfoPO;
import com.wk.crowd.pojo.po.MemberPO;
import com.wk.crowd.pojo.vo.MemberSignSuccessVO;
import com.wk.crowd.pojo.vo.MemberVO;
import com.wk.crowd.util.CrowdConstant;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class MemberController {

	@Autowired
	RedisOperationRemoteService redisRemoteService;		//装配操作Redis的类

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	DataBaseOperationRemoteService databasesRemoteService;

	//Spring会根据@Value注解中的表达式读取yml/properties配置文件给成员变量设置对应的值
	@Value("${crowd.short.message.appCode}")
	private String appcode;

	@RequestMapping("get/Member/Launch/InfoPO/By/Token")
	public ResultEntity<MemberLaunchInfoPO> getMemberLaunchInfoPOByToken(@RequestParam("token") String token){
		//根据token查询memberID
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(token);
		String memberId = resultEntity.getData();
		if (memberId == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		//根据memberID查询项目发起人信息
		return databasesRemoteService.getLaunchInfoByMemberId(memberId);
	}


	/**
	 * 退出，根据key删除Redis中对应的value
	 * @param token
	 * @return
	 */
	@RequestMapping("member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token){
		return redisRemoteService.removeByKey(token);
	}

	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginacct") String loginacct,
			@RequestParam("userpswd") String userpswd){
		//根据登录账号从数据库查询MemberPO对象
		ResultEntity<MemberPO> resultEntity = databasesRemoteService.retrieveMemberByLoginAcct(loginacct);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return ResultEntity.failed(resultEntity.getMessage());
		}

		//获取MemberPO对象
		MemberPO memberPO = resultEntity.getData();
		//判断MemberPO对象是否为空
		if (memberPO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		//获取从数据库中查询到的密码
		String userpswdByDataBase = memberPO.getUserpswd();

		//比较数据库加密后的密码和前台传入的密码
		boolean matches = passwordEncoder.matches(userpswd, userpswdByDataBase);
		if (!matches) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}

		//对比成功，生成token
		String token = CrowdUtils.generateRedisKey(CrowdConstant.REDIS_MEMBER_SING_TOKEN_PREFIX);
		//token作为key，userid作为value，过期时间30分钟存入Redis
		String id = memberPO.getId()+"";
		ResultEntity<String> resultEntitySaveToken = redisRemoteService.saveNormalStringKeyValue(token, id, 30);
		if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())){
			return ResultEntity.failed(resultEntitySaveToken.getMessage());
		}
		//账号密码验证成功，封装对象
		MemberSignSuccessVO signSuccessVO = new MemberSignSuccessVO();
		//将memberPO复制到signSuccessVO对象中
		BeanUtils.copyProperties(memberPO,signSuccessVO);
		signSuccessVO.setToken(token);
		return ResultEntity.successWithData(signSuccessVO);
	}

	/**
	 * 注册
	 * @param memberVO
	 * @return
	 */
	@RequestMapping("member/manager/register")
	public ResultEntity<String> register(@RequestBody() MemberVO memberVO){
		//检查验证码是否有效
		String randomCode = memberVO.getRandomCode();
		if(!CrowdUtils.strEffectiveCheck(randomCode)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_INVALID);
		}

		//检查手机号是否有效
		String phoneNum = memberVO.getPhoneNum();
		if(!CrowdUtils.strEffectiveCheck(phoneNum)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
		}

		//拼接验证码的key
		String codeKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX+phoneNum;

		//远程调用Redis_provider的方法获取key对应的值
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(codeKey);
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return resultEntity;
		}

		//获取Redis中key对应的value
		String randomCodeRedis = resultEntity.getData();
		if(!CrowdUtils.strEffectiveCheck(randomCodeRedis)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
		}

		//判断前台传入的值和Redis中的值是否相等
		if(!Objects.equals(randomCode,randomCodeRedis)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_MATCH);
		}

		//如果验证码匹配需要删除当前key对应的验证码
		ResultEntity<String> resultEntity1 = redisRemoteService.removeByKey(codeKey);
		if(ResultEntity.FAILED.equals(resultEntity1.getResult())){
			return resultEntity1;
		}

		//远程调用database_provider检查账号是否存在
		String loginacct = memberVO.getLoginacct();
		ResultEntity<Integer> integerResultEntity = databasesRemoteService.retrieveLoginAcctCount(loginacct);
		if(ResultEntity.FAILED.equals(integerResultEntity.getResult())){
			return ResultEntity.failed(integerResultEntity.getMessage());
		}

		Integer count = integerResultEntity.getData();
		//账号被占用，返回失败信息
		if(count>0){
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
		}

		//密码加密
		String userpswd = memberVO.getUserpswd();
		String encode = passwordEncoder.encode(userpswd);
		memberVO.setUserpswd(encode);

		MemberPO memberPO = new MemberPO();
		//将memberVO对象中的值复制到memberPO对象中
		BeanUtils.copyProperties(memberVO,memberPO);
		//远程调用database_provider保存注册信息
		return databasesRemoteService.saveMemberRemote(memberPO);
	}

	/**
	 * 发送短信验证码
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping("member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum")String phoneNum){
		//验证手机号
		if(!CrowdUtils.strEffectiveCheck(phoneNum)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		//生成验证码
		int length = 4;
		String code = CrowdUtils.randomCode(length);

		//保存到Redis
		int timeoutMinute = 15;		//验证码默认过期时间15分钟
		//Redis的key是固定前缀+手机号
		String normalKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX+phoneNum;
		ResultEntity<String> resultEntity = redisRemoteService.saveNormalStringKeyValue(normalKey, code, timeoutMinute);
		//判断key是否保存成功，如果失败就直接返回
		if(ResultEntity.FAILED.equals(resultEntity.getResult())){
			return resultEntity;
		}
		//验证码成功添加到Redis后发送到用户手机
		try {
			CrowdUtils.sendMessage(appcode,code,phoneNum);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

}
