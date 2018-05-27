package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 22:03 2018/5/1
 * @Modified By:
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUserId(Long userId);

    UserRole findByUserIdAndRoleId(Long userId, Integer roleId);

    void deleteByUserId(Long userId);

    void deleteByRoleId(Integer roleId);
}
