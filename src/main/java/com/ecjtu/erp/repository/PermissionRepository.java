package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:44 2018/4/30
 * @Modified By:
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByNameLike(String name);

    List<Permission> findAllByRoleRoleId(Integer roleId);

    List<Permission> findAllByPermissionIdIn(List<Long> permissionId);
}
