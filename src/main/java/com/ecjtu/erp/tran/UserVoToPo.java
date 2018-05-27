package com.ecjtu.erp.tran;

import com.ecjtu.erp.enums.UserStatu;
import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

/**
 * @Author: 胡森
 * @Description: 用户的vo转为po
 * @Date: Created in 16:15 2018/4/30
 * @Modified By:
 */
@Component
public class UserVoToPo implements Function<UserVO, User> {

    /**
     * 默认密码
     */
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User apply(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        //初始化密码
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        //初始化用户名，默认为手机号
        user.setUsername(userVO.getPhone());
        //默认启用
        user.setStatu(UserStatu.ENABLE.getValue());
        return user;
    }
}
