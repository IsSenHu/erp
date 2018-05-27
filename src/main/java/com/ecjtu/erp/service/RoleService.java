package com.ecjtu.erp.service;

import com.ecjtu.erp.model.vo.RoleVO;
import com.ecjtu.erp.model.vo.permission.EditPermissionVo;
import com.ecjtu.erp.model.vo.role.EditRoleVo;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.model.vo.UserRolesAndAllRoles;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:03 2018/4/30
 * @Modified By:
 */
public interface RoleService extends BaseService {
    JsonResult saveRole(RoleVO roleVO);

    JsonResult deleteRole(Integer roleId);

    List<RoleVO> search(String name);

    RoleVO findRoleById(Integer roleId);

    UserRolesAndAllRoles UserRolesAndAllRoles(Long userId);

    EditRoleVo getEditRoleVo(Long userId, String op);

    EditPermissionVo findPermissionHaveByPermissionId(Integer roleId, String op);

    JsonResult addRolePermissions(Integer roleId, String permissionIdStr);

    JsonResult deleteRolePermissions(Integer roleId, String permissionIdStr);
}
