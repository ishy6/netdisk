package com.netdisk.common;

import com.alibaba.fastjson.JSON;
import com.netdisk.common.controller.SystemController;
import com.netdisk.common.mapper.SystemMapper;
import com.netdisk.common.po.Login;
import com.netdisk.common.po.SystemUser;
import com.netdisk.common.po.User;
import com.netdisk.common.service.SystemSevice;
import com.netdisk.common.service.impl.SystemServiceimpl;
import com.netdisk.common.util.RedisDao;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetdiskCommonApplicationTests {

	public static Logger logger = LoggerFactory.getLogger(NetdiskCommonApplicationTests.class);


	@Autowired
	RedisDao redisDao;

	@Autowired
	private SystemSevice service;

	@Autowired
	private SystemMapper systemMapper;

	@Autowired
	private SystemController systemController;

	@Test
	public void testRedis() {
		/*String token = UUID.randomUUID().toString();
		System.out.println(token);
		System.out.println(token.replace("-",""));
		redisDao.setKey("name","linlianru",5);
		System.out.println(redisDao.getValue("name"));
		try {
			Thread.sleep(5000);
		}catch (Exception e){
			e.getStackTrace();
		}

		System.out.println("here is 5 seconds after" + redisDao.getValue("name"));*/
		/*Login login = new Login();
		List list = new ArrayList();
		list.add(login);
		login.setUserName("linrulin");
		login.setPassWord("123456");
		String jsonString = JSON.toJSONString(login);
		System.out.println(jsonString);
		System.out.println("=========================");
		System.out.println(JSON.parseObject(jsonString,Login.class));
		System.out.println("=========================");
		System.out.println(list);
		System.out.println(JSON.toJSONString(list));
		service.getEncrypt("lianruLin123456");*/
		SystemUser user = new SystemUser();
		user.setUSER_ID("1");
		user.setUSER_PASSWORD("123456");
		Boolean result = service.updateUserInfo(user);
		System.out.println(result);
	}


}
