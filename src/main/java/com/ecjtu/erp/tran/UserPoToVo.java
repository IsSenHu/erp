package com.ecjtu.erp.tran;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

/**
 * @Author: 胡森
 * @Description: User的po转vo
 * @Date: Created in 17:22 2018/4/30
 * @Modified By:
 */
@Component
public class UserPoToVo implements Function<User, UserVO> {
    @Override
    public UserVO apply(User user) {
        UserVO vo = new UserVO();
        if(!Objects.isNull(user)){
            vo.setUserId(user.getUserId());
            vo.setUsername(user.getUsername());
            vo.setPassword(user.getPassword());
            vo.setName(user.getName());
            vo.setEmail(user.getEmail());
            vo.setPhone(user.getPhone());
            vo.setStatu(user.getStatu());
        }
        return vo;
    }
}
