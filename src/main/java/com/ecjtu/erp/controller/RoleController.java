package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.vo.RoleVO;
import com.ecjtu.erp.model.vo.UserRolesAndAllRoles;
import com.ecjtu.erp.model.vo.permission.EditPermissionVo;
import com.ecjtu.erp.model.vo.role.EditRoleVo;
import com.ecjtu.erp.service.RoleService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 19:34 2018/4/30
 * @Modified By:
 */
@RestController
public class RoleController {

    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 角色管理页面
     * @param name
     * @return
     */
    @GetMapping("/roles")
    private ModelAndView roles(String name){
        List<RoleVO> roleVOS = roleService.search(name);
        return new ModelAndView("pages/tables/roles")
                .addObject("roles", roleVOS)
                .addObject("menusShow", roleService.menusAndChilds());
    }

    /**
     * 添加或修改角色
     * @param roleVO
     * @return
     */
    @PostMapping("/saveRole")
    private JsonResult saveRole(@Valid @RequestBody RoleVO roleVO, BindingResult result){
        if(result.hasErrors()){
            log.info("输入错误");
            return new JsonResult(400, result.getFieldError());
        }
        return roleService.saveRole(roleVO);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @PostMapping("/deleteRole")
    private JsonResult deleteRole(Integer roleId){
        return roleService.deleteRole(roleId);
    }

    @PostMapping("/findRoleById")
    private RoleVO findRoleById(Integer roleId){
        return roleService.findRoleById(roleId);
    }

    /**
     * 查询目前用户拥有的角色和未拥有的角色
     * @return
     */
    @PostMapping("/UserRolesAndAllRoles")
    private UserRolesAndAllRoles UserRolesAndAllRoles(Long userId){
        return roleService.UserRolesAndAllRoles(userId);
    }

    /**
     * 返回用户拥有和未拥有的角色
     * @param userId
     * @return
     */
    @PostMapping("/getEditRoleVo")
    private EditRoleVo getEditRoleVo(Long userId, String op){
        return roleService.getEditRoleVo(userId, op);
    }

    /**
     * 返回用户已拥有和未拥有的权限
     * @param roleId
     * @param op
     * @return
     */
    @PostMapping("/findPermissionHaveByRoleId")
    private EditPermissionVo findPermissionHaveByPermissionId(Integer roleId, String op){
        return roleService.findPermissionHaveByPermissionId(roleId, op);
    }

    /**
     * 添加角色的权限
     * @param roleId
     * @param permissionIdStr
     * @return
     */
    @PostMapping("/addRolePermissions")
    private JsonResult addRolePermissions(Integer roleId, String permissionIdStr){
        return roleService.addRolePermissions(roleId, permissionIdStr);
    }

    /**
     * 删除角色的权限
     * @param roleId
     * @param permissionIdStr
     * @return
     */
    @PostMapping("/deleteRolePermissions")
    private JsonResult deleteRolePermissions(Integer roleId, String permissionIdStr){
        return roleService.deleteRolePermissions(roleId, permissionIdStr);
    }
}
