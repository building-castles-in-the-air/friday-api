package com.github.friday;

import com.github.friday.sys.domain.entity.User;
import com.github.friday.sys.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setUsername("taven");
		user.setPassword(DigestUtils.md5Hex("123"));
		user.setRealName("殷天文先生");
		user.setIsDel(false);
		user.setCreateTime(new Date());
		user.setCreateUser("sys");
		userMapper.insert(user);

		List<User> users = userMapper.selectByExample(null);
		System.out.println(users);
	}

}
