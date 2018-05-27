package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.Role;
import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.UserRole;
import com.ecjtu.erp.model.vo.MenuVO;
import com.ecjtu.erp.model.vo.MenusAndChilds;
import com.ecjtu.erp.repository.MenuRepository;
import com.ecjtu.erp.repository.PermissionRepository;
import com.ecjtu.erp.repository.RoleRepository;
import com.ecjtu.erp.repository.UserRoleRepository;
import com.ecjtu.erp.service.BaseService;
import com.ecjtu.erp.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:44 2018/5/1
 * @Modified By:
 */
public class BaseServiceImpl implements BaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<MenusAndChilds> menusAndChilds() {
        List<MenusAndChilds> list = new ArrayList<>();
        User user = getUser();
        if(Objects.isNull(user)){
            log.info("######用户未登录######");
        }else {
            log.info("######登录的用户为:{}######", user);
            List<Role> roles = userRoleRepository.findAllByUserId(user.getUserId())
                    .stream().map(userRole -> roleRepository.findById(userRole.getRoleId()).get()).collect(Collectors.toList());
            roles.stream().forEach(role -> permissionRepository.findAllByRoleRoleId(role.getRoleId()).forEach(permission -> {
                Menu menu = permission.getMenu();
                if(!Objects.isNull(menu)){
                    MenusAndChilds menusAndChilds = new MenusAndChilds();
                    MenuVO father = new MenuVO();
                    father.setName(menu.getName());
                    father.setUrl(menu.getUrl());
                    father.setIcon(menu.getIcon());
                    menusAndChilds.setMajor(father);

                    List<Menu> childs = menuRepository.findAllByFatherMenuId(menu.getMenuId());
                    List<MenuVO> vos = new ArrayList<>();
                    for (Menu child : childs){
                        MenuVO vo = new MenuVO();
                        vo.setName(child.getName());
                        vo.setUrl(child.getUrl());
                        vos.add(vo);
                    }
                    menusAndChilds.setChilds(vos);
                    list.add(menusAndChilds);
                }
            }));

        }
        return list;
    }

    /**
     * 获取已认证的用户信息
     * @return
     */
    public static User getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
