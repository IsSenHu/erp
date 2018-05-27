package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:18 2018/4/30
 * @Modified By:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
}
