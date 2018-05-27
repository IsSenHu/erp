package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.Menu;
import com.ecjtu.erp.model.Permission;
import com.ecjtu.erp.model.vo.PermissionVO;
import com.ecjtu.erp.repository.MenuRepository;
import com.ecjtu.erp.repository.PermissionRepository;
import com.ecjtu.erp.service.PermissionService;
import com.ecjtu.erp.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:43 2018/4/30
 * @Modified By:
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {
    private final static Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    @Transactional
    public JsonResult savePermission(PermissionVO permissionVO) {
        if(Objects.isNull(permissionVO)){
            return new JsonResult(500);
        }
        if(Objects.isNull(permissionVO.getPermissionId())){
            log.info("添加权限:{}", permissionVO);
            try{
                Permission permission = new Permission();
                permission.setName(permissionVO.getName());
                permission.setDescription(permissionVO.getDescription());
                Permission save = permissionRepository.save(permission);
                return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
            }catch (Exception e){
                log.error("新增权限失败，发生异常:{}", e.getMessage());
                return new JsonResult(500);
            }
        }else {
            log.info("修改权限信息:{}", permissionVO);
            try {
                Permission find = permissionRepository.findById(permissionVO.getPermissionId()).get();
                find.setName(permissionVO.getName());
                find.setDescription(permissionVO.getDescription());
                Permission save = permissionRepository.save(find);
                return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
            }catch (Exception e){
                log.error("修改权限失败，发生异常:{}", e.getMessage());
                return new JsonResult(500);
            }
        }
    }

    @Override
    @Transactional
    public JsonResult deletePermission(Long permissionId) {
        if(Objects.isNull(permissionId)){
            return new JsonResult(400);
        }
        try {
            /**
             * 1,删除权限的时候，将该权限的菜单的权限置为空
             * */
            Menu menu = menuRepository.findByPermissionPermissionId(permissionId);
            if(!Objects.isNull(menu)){
                menu.setPermission(null);
                menuRepository.save(menu);
            }
            permissionRepository.deleteById(permissionId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<PermissionVO> findAllPermissionByName(String permissionName) {
        List<Permission> permissions;
        if(StringUtils.isNotBlank(permissionName)){
            permissions = permissionRepository.findAllByNameLike("%" + permissionName + "%");
        }else {
            permissions = permissionRepository.findAll();
        }
        List<PermissionVO> vos = new ArrayList<>();
        permissions.stream().forEach(x -> {
            PermissionVO vo = new PermissionVO();
            vo.setPermissionId(x.getPermissionId());
            vo.setName(x.getName());
            vo.setDescription(x.getDescription());
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public PermissionVO findPermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).get();
        PermissionVO vo = new PermissionVO();
        vo.setPermissionId(permissionId);
        vo.setName(permission.getName());
        vo.setDescription(permission.getDescription());
        return vo;
    }

    @Override
    @Transactional
    public JsonResult setMajorMenu(Long permissionId, Long menuId) {
        if(Objects.isNull(permissionId) || Objects.isNull(menuId)){
            log.info("权限ID为空或菜单ID为空，错误");
            return new JsonResult(500);
        }
        try {
            //判断是否可以进行设置
            Menu menu = menuRepository.findById(menuId).get();
            if(Objects.isNull(menu) || !Objects.isNull(menu.getPermission())){
                log.info("该主菜单的当前状态不能被设置");
                return new JsonResult(500);
            }
            Permission permission = permissionRepository.findById(permissionId).get();
            if(Objects.isNull(permission)){
                log.info("该权限不存在");
                return new JsonResult(500);
            }
            //如果该权限已经有主菜单了，则将先去的主菜单置为可被设置的状态 即它的权限为null
            if(Objects.isNull(permission.getMenu())){
                //直接互相设置就可以了
                permission.setMenu(menu);
                permissionRepository.save(permission);
                menu.setPermission(permission);
                menuRepository.save(menu);
            }else {
                Menu oldMenu = menuRepository.findById(permission.getMenu().getMenuId()).get();
                oldMenu.setPermission(null);
                menuRepository.save(oldMenu);
                permission.setMenu(menu);
                permissionRepository.save(permission);
                menu.setPermission(permission);
                menuRepository.save(menu);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("主菜单设置错误，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult cancelMenu(Long permissionId) {
        if(Objects.isNull(permissionId)){
            log.info("权限的ID为空，错误");
            return new JsonResult(500);
        }
        try {
            Permission permission = permissionRepository.findById(permissionId).get();
            if(Objects.isNull(permission.getMenu())){
                //没有菜单，不可取消
                return new JsonResult(401);
            }
            Menu menu = menuRepository.findByPermissionPermissionId(permissionId);
            if(Objects.isNull(permission) || Objects.isNull(menu)){
                return new JsonResult(500);
            }
            permission.setMenu(null);
            permissionRepository.save(permission);
            menu.setPermission(null);
            menuRepository.save(menu);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("取消权限的菜单失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
