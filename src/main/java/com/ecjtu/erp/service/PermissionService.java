package com.ecjtu.erp.service;

import com.ecjtu.erp.model.vo.PermissionVO;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:43 2018/4/30
 * @Modified By:
 */
public interface PermissionService extends BaseService {
    JsonResult savePermission(PermissionVO permissionVO);

    JsonResult deletePermission(Long permissionId);

    List<PermissionVO> findAllPermissionByName(String permissionName);

    PermissionVO findPermissionById(Long permissionId);

    JsonResult setMajorMenu(Long permissionId, Long menuId);

    JsonResult cancelMenu(Long permissionId);
}
