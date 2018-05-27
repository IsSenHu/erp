package com.ecjtu.erp.controller;

import com.ecjtu.erp.service.PermissionService;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.model.vo.PermissionVO;
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
 * @Date: Created in 20:40 2018/4/30
 * @Modified By:
 */
@RestController
public class PermissionController {

    private final static Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限管理页面
     * @param permissionName
     * @return
     */
    @GetMapping("/permissions")
    private ModelAndView permissions(String permissionName){
        List<PermissionVO> vos = permissionService.findAllPermissionByName(permissionName);
        return new ModelAndView("pages/tables/permissions")
                .addObject("permissions", vos)
                .addObject("menusShow", permissionService.menusAndChilds());
    }

    /**
     * 修改或新增权限
     * @param permissionVO
     * @param result
     * @return
     */
    @PostMapping("/savePermission")
    private JsonResult savePermission(@Valid @RequestBody PermissionVO permissionVO, BindingResult result){
        if(result.hasErrors()){
            log.info("输入有误");
            return new JsonResult(400, result.getFieldError());
        }
        return permissionService.savePermission(permissionVO);
    }

    /**
     * 删除权限
     * @param permissionId
     * @return
     */
    @PostMapping("/deletePermission")
    private JsonResult deletePermission(Long permissionId){
        return permissionService.deletePermission(permissionId);
    }

    /**
     * 根据ID查询权限
     * @param permissionId
     * @return
     */
    @PostMapping("/findPermissionById")
    private PermissionVO findPermissionById(Long permissionId){
        return permissionService.findPermissionById(permissionId);
    }

    /**
     * 设置主菜单
     * @param permissionId
     * @param menuId
     * @return
     */
    @PostMapping("/setMajorMenu")
    private JsonResult setMajorMenu(Long permissionId, Long menuId){
        return permissionService.setMajorMenu(permissionId, menuId);
    }

    /**
     * 取消主菜单
     * @param permissionId
     * @return
     */
    @PostMapping("/cancelMenu")
    private JsonResult cancelMenu(Long permissionId){
        return permissionService.cancelMenu(permissionId);
    }
 }
