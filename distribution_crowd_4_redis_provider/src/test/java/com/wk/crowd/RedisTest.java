package com.wk.crowd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisTemplate<Object,Object> redisTemplate;		//适用于所有数据

	@Autowired
	private StringRedisTemplate stringRedisTemplate;		//只能用于字符串类型数据

	@Test
	public void redisTemplateTest(){
		//获取Redis操作器
		ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
//		ops.set("k1","v1");
		System.out.println("ops = " + ops.get("k1"));
	}

	@Test
	public void stringRedisTest(){
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		ops.set("k2","v2");
		System.out.println("ops = " + ops.get("k2"));
	}
}
