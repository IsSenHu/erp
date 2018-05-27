package com.ecjtu.erp;

import com.ecjtu.erp.model.good.CustomsCode;
import com.ecjtu.erp.model.vo.UserVO;
import com.ecjtu.erp.service.BaseService;
import com.ecjtu.erp.service.CustomsCodeService;
import com.ecjtu.erp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErpApplicationTests {
	@Autowired
	private UserService userService;

	@Autowired
	private CustomsCodeService customsCodeService;

	@Autowired
	private BaseService baseService;

	@Test
	public void contextLoads() {
		for(int i = 1; i < 10; i++){
			UserVO userVO = new UserVO();
			userVO.setName("胡森" + i);
			userVO.setEmail(i + "@qq.com");
			userVO.setPhone("1234567" + i);
			userService.saveUser(userVO);
		}
	}
	@Test
	public void test(){
	}

}
