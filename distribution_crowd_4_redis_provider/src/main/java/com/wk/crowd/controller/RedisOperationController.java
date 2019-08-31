package com.wk.crowd.controller;

import com.wk.crowd.pojo.ResultEntity;
import com.wk.crowd.util.CrowdConstant;
import com.wk.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisOperationController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 将字符串类型的键值对保存到Redis时调用的远程方法
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute 超时时间（单位：分钟）
	 *      -1表示无过期时间
	 *      正数表示过期时间分钟数
	 *      0和null值不接受
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(
			@RequestParam("normalKey") String normalKey,
			@RequestParam("normalValue") String normalValue,
			@RequestParam("timeoutMinute") Integer timeoutMinute){
		//对存入Redis中的数据进行验证
		if(!CrowdUtils.strEffectiveCheck(normalKey)||!CrowdUtils.strEffectiveCheck(normalValue)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		//验证过期时间
		if(null==timeoutMinute||0==timeoutMinute){
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_TIME_OUT_INVALID);
		}
		//获取Redis执行器
		ValueOperations<String, String> oper = stringRedisTemplate.opsForValue();
		//timeoutMinute的值为-1则不设置过期时间
		if(-1==timeoutMinute){
			try {
				oper.set(normalKey,normalValue);
			} catch (Exception e) {
				e.printStackTrace();
				//返回失败结果
				return ResultEntity.failed(e.getMessage());
			}
			//返回成功结果
			return ResultEntity.successNoData();
		}

		//设置key的过期时间，单位：分钟
		try {
			oper.set(normalKey,normalValue,timeoutMinute, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
		return ResultEntity.successNoData();
	}

	/**
	 * 根据key查询对应value时调用的远程方法
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey){
		//对存入Redis中的数据进行验证
		if(!CrowdUtils.strEffectiveCheck(normalKey)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		try {
			String str = stringRedisTemplate.opsForValue().get(normalKey);
			return ResultEntity.successWithData(str);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

	/**
	 * 根据key删除对应value时调用的远程方法
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key){
		//对存入Redis中的数据进行验证
		if(!CrowdUtils.strEffectiveCheck(key)){
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		try {
			stringRedisTemplate.delete(key);
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}
	}

}
