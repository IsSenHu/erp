package com.ecjtu.erp.service;

import com.ecjtu.erp.model.vo.Index;
import com.ecjtu.erp.model.vo.UserVO;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.util.PageUtil;
import com.ecjtu.erp.model.vo.SearchUserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:09 2018/4/30
 * @Modified By:
 */
public interface UserService extends BaseService, UserDetailsService{
    JsonResult saveUser(UserVO userVO);

    PageUtil<UserVO> searchUser(SearchUserVO searchUserVO);

    JsonResult deleteUser(Long userId);

    JsonResult changeUserStatus(Long userId);

    JsonResult addUserRoles(Long userId, String roleIdStr);

    JsonResult deleteUserRoles(Long userId, String roleIdStr);

    List<String> allUrls();

    Index index();
}
