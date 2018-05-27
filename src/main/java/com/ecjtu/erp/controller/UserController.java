package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.vo.SearchUserVO;
import com.ecjtu.erp.model.vo.UserVO;
import com.ecjtu.erp.service.UserService;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:22 2018/4/30
 * @Modified By:
 */
@RestController
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 用户管理页面
     * @param searchUserVO
     * @return
     */
    @GetMapping("/users")
    private ModelAndView users(SearchUserVO searchUserVO){
        PageUtil<UserVO> page = userService.searchUser(searchUserVO);
        return new ModelAndView("pages/tables/users")
                .addObject("page", page)
                .addObject("menusShow", userService.menusAndChilds());
    }

    /**
     * 保存用户信息
     * @param userVO
     * @return
     */
    @PostMapping("/saveUser")
    private JsonResult<List<FieldError>> saveUser(@RequestBody @Valid UserVO userVO, BindingResult result){
        log.info("接收到的用户数据是:{}", userVO);
        if(result.hasErrors()){
            log.info("输入有错误");
            return new JsonResult(400, result.getFieldErrors());
        }
        return userService.saveUser(userVO);
    }

    /**
     * 根据ID删除用户
     * @param userId
     * @return
     */
    @PostMapping("/deleteUser")
    private JsonResult deleteUser(Long userId){
        return userService.deleteUser(userId);
    }

    /**
     * 改变用户的状态
     * @param userId
     * @return
     */
    @PostMapping("/changeUserStatus")
    private JsonResult changeUserStatus(Long userId){
        return userService.changeUserStatus(userId);
    }

    /**
     * 新增用户的角色
     * @param userId
     * @param roleIdStr
     * @return
     */
    @PostMapping("/addUserRoles")
    private JsonResult addUserRoles(Long userId, String roleIdStr){
        return userService.addUserRoles(userId, roleIdStr);
    }

    /**
     * 删除用户的角色
     * @param userId
     * @param roleIdStr
     * @return
     */
    @PostMapping("/deleteUserRoles")
    private JsonResult deleteUserRoles(Long userId, String roleIdStr){
        return userService.deleteUserRoles(userId, roleIdStr);
    }

    /**
     * 转到登录页面
     * @return
     */
    @GetMapping("/login")
    private ModelAndView toLogin(){
        return new ModelAndView("pages/examples/login");
    }


}
