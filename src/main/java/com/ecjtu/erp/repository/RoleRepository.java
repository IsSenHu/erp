package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 20:05 2018/4/30
 * @Modified By:
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByNameLike(String name);
}
