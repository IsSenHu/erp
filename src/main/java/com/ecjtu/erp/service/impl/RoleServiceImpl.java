package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.UserRole;
import com.ecjtu.erp.model.vo.PermissionVO;
import com.ecjtu.erp.model.vo.RoleVO;
import com.ecjtu.erp.model.vo.UserRolesAndAllRoles;
import com.ecjtu.erp.model.vo.permission.EditPermissionVo;
import com.ecjtu.erp.model.vo.role.EditRoleVo;
import com.ecjtu.erp.repository.PermissionRepository;
import com.ecjtu.erp.service.RoleService;
import com.ecjtu.erp.util.JsonResult;
import com.ecjtu.erp.model.Permission;
import com.ecjtu.erp.model.Role;
import com.ecjtu.erp.repository.RoleRepository;
import com.ecjtu.erp.repository.UserRoleRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:03 2018/4/30
 * @Modified By:
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public JsonResult saveRole(@Valid RoleVO roleVO) {
        if(Objects.isNull(roleVO)){
            return new JsonResult(500);
        }
        if(Objects.isNull(roleVO.getRoleId())){
            log.info("添加角色:{}", roleVO);
            //添加
            try {
                Role role = new Role();
                role.setName(roleVO.getName());
                role.setDescription(roleVO.getDescription());
                Role save = roleRepository.save(role);
                return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
            }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(500);
            }
        }else {
            log.info("修改角色:{}", roleVO);
            //修改
           try {
               Role find = roleRepository.findById(roleVO.getRoleId()).get();
               find.setName(roleVO.getName());
               find.setDescription(roleVO.getDescription());
               Role save = roleRepository.save(find);
               return Objects.isNull(save) ? new JsonResult(500) : new JsonResult(200);
           }catch (Exception e){
               e.printStackTrace();
               return new JsonResult(500);
           }
        }
    }

    @Override
    @Transactional
    public JsonResult deleteRole(Integer roleId) {
        if(Objects.isNull(roleId)){
            return new JsonResult(400);
        }
        try {
            /**
             *  1,删除角色的时候，把所属该角色的所有用户角色关系删除
             *  2,将先前拥有的权限的角色置为空
             */
            userRoleRepository.deleteByRoleId(roleId);
            List<Permission> permissions = permissionRepository.findAllByRoleRoleId(roleId);
            if(CollectionUtils.isNotEmpty(permissions)){
                permissions.stream().forEach(permission -> {
                    permission.setRole(null);
                    permissionRepository.save(permission);
                });
            }
            roleRepository.deleteById(roleId);
            return new JsonResult(200);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public List<RoleVO> search(String name) {
        List<Role> roles;
        if(StringUtils.isNotBlank(name)){
            roles = roleRepository.findAllByNameLike("%" + name + "%");
        }else {
            roles = roleRepository.findAll();
        }
        List<RoleVO> vos = new ArrayList<>();
        roles.stream().forEach(x -> {
            RoleVO vo = new RoleVO(x.getRoleId(), x.getName(), x.getDescription());
            vos.add(vo);
        });
        return vos;
    }

    @Override
    public RoleVO findRoleById(Integer roleId) {
        if(Objects.isNull(roleId)){
            return new RoleVO();
        }
        Role role = roleRepository.findById(roleId).get();
        RoleVO vo = new RoleVO(roleId, role.getName(), role.getDescription());
        return vo;
    }

    @Override
    public UserRolesAndAllRoles UserRolesAndAllRoles(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(userId);
        List<RoleVO> vos = new ArrayList<>();
        List<Integer> userRoleIdList = new ArrayList<>();
        for(UserRole userRole : userRoles){
            userRoleIdList.add(userRole.getRoleId());
            Role role = roleRepository.findById(userRole.getRoleId()).get();
            RoleVO vo = new RoleVO(role.getRoleId(), role.getName(), role.getDescription());
            vos.add(vo);
        }
        List<Role> roles = roleRepository.findAll();
        //过滤获掉当前用户没拥有的角色
        List<RoleVO> voList = new ArrayList<>();
        roles.stream().filter(role -> !userRoleIdList.contains(role.getRoleId())).forEach(role -> {
            RoleVO vo = new RoleVO(role.getRoleId(), role.getName(), role.getDescription());
            voList.add(vo);
        });
        UserRolesAndAllRoles userRolesAndAllRoles = new UserRolesAndAllRoles();
        userRolesAndAllRoles.setUserRoles(vos);
        userRolesAndAllRoles.setOtherRoles(voList);
        return userRolesAndAllRoles;
    }

    @Override
    public EditRoleVo getEditRoleVo(Long userId, String op) {
        log.info("用户ID为:{}", userId);
        List<UserRole> userRoleList = userRoleRepository.findAllByUserId(userId);
        EditRoleVo editRoleVo = new EditRoleVo();
        List<RoleVO> have = userRoleList.stream()
                .map(userRole -> roleRepository.findById(userRole.getRoleId()).get())
                .map(role -> new RoleVO(role.getRoleId(), role.getName(), role.getDescription()))
                .collect(Collectors.toList());
        log.info("查询到的已拥有的角色数量为:{}", have.size());
        editRoleVo.setHave(have);
        if("add".equals(op)){
            List<RoleVO> none = roleRepository.findAll().stream()
                    .filter(role -> !userRoleList.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList()).contains(role.getRoleId()))
                    .map(role -> new RoleVO(role.getRoleId(), role.getName(), role.getDescription()))
                    .collect(Collectors.toList());
            log.info("查询到的未拥有的角色数量为:{}", none.size());
            editRoleVo.setNone(none);
        }
        return editRoleVo;
    }

    @Override
    public EditPermissionVo findPermissionHaveByPermissionId(Integer roleId, String op) {
        EditPermissionVo edit = new EditPermissionVo();
        if(Objects.isNull(roleId)){
            log.info("角色ID为空，错误");
            return edit;
        }
        log.info("角色ID为:{}", roleId);
        List<Permission> have = permissionRepository.findAllByRoleRoleId(roleId);
        log.info("查询到已拥有的权限数量为:{}", have.size());
        List<Permission> all = permissionRepository.findAll();
        log.info("查询到所有的权限数量为:{}", all.size());
        List<PermissionVO> haveVo = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(have)){
            haveVo = have.stream()
                    .map(permission -> new PermissionVO(permission.getPermissionId(), permission.getName(), permission.getDescription()))
                    .collect(Collectors.toList());
        }
        if(Objects.equals("add", op)){
            //得到所有可以添加的权限 还没被角色所拥有的权限 即角色为空的权限
            List<Permission> enableAdd = all.stream().filter(permission -> Objects.isNull(permission.getRole())).collect(Collectors.toList());
            log.info("过滤的可以添加的权限数量为:{}", enableAdd.size());
            //转为vo
            List<PermissionVO> enableAddVo = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(enableAdd)){
                enableAddVo = enableAdd.stream()
                        .map(permission -> new PermissionVO(permission.getPermissionId(), permission.getName(), permission.getDescription()))
                        .collect(Collectors.toList());
            }
            edit.setHave(haveVo);
            edit.setNone(enableAddVo);
        }else {
            //得到已拥有的所有的权限
            edit.setHave(haveVo);
        }
        return edit;
    }

    @Override
    @Transactional
    public JsonResult addRolePermissions(Integer roleId, String permissionIdStr) {
        if(Objects.isNull(roleId)){
            log.info("角色ID为空，错误");
            return new JsonResult(500);
        }
        if(StringUtils.isBlank(permissionIdStr)){
            log.info("必须至少选择一个权限进行添加");
            return new JsonResult(400);
        }
        try {
            List<Permission> add = Arrays.stream(permissionIdStr.split(","))
                    .map(roleIdStr -> permissionRepository.findById(Long.valueOf(roleIdStr)).get()).collect(Collectors.toList());
            log.info("要添加的权限数量是:{}", add.size());
            //校验是否都可添加 并且得到可行的添加权限集合
            List<Permission> enableAdd = add.stream().filter(permission -> Objects.isNull(permission.getRole())).collect(Collectors.toList());
            log.info("验证通过的可添加的权限数量是:{}", enableAdd.size());
            //添加该角色的权限
            Role role = roleRepository.findById(roleId).get();
            if(Objects.isNull(role)){
                return new JsonResult(500);
            }
            enableAdd.stream().forEach(permission -> {
                permission.setRole(role);
                permissionRepository.save(permission);
            });
            return new JsonResult(200);
        }catch (Exception e){
            log.error("添加权限出错，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional
    public JsonResult deleteRolePermissions(Integer roleId, String permissionIdStr) {
        if(Objects.isNull(roleId)){
            log.info("角色ID为空，错误");
            return new JsonResult(500);
        }
        if(StringUtils.isBlank(permissionIdStr)){
            log.info("请至少选择一个角色");
            return new JsonResult(400);
        }
        try {
            List<Long> permissionId = Arrays.stream(permissionIdStr.split(",")).map(idStr -> Long.valueOf(idStr)).collect(Collectors.toList());
            //要确定该角色拥有该权限才能移除
            List<Permission> permissions = permissionRepository.findAllByPermissionIdIn(permissionId);
            Role role = roleRepository.findById(roleId).get();
            if(Objects.isNull(role)){
                log.info("查询不到该角色:{}", roleId);
                return new JsonResult(500);
            }
            for(Permission permission : permissions){
                if(Objects.isNull(permission.getRole()) || !Objects.equals(role.getRoleId(), permission.getRole().getRoleId())){
                    log.info("有错误的数据，终止:{}", Objects.isNull(permission.getRole()) + "##" + Objects.equals(role.getRoleId(), permission.getRole().getRoleId()));
                    return new JsonResult(500);
                }
            }
            permissions.stream().forEach(permission -> {
                permission.setRole(null);
                permissionRepository.save(permission);
            });
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除角色的权限失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }
}
