package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 4:58 2018/5/1
 * @Modified By:
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByNameLikeAndIsMajor(String name, Integer isMajor);

    List<Menu> findAllByIsMajor(Integer isMajor);

    List<Menu> findAllByFatherMenuId(Long fatherMenuId);

    Menu findByPermissionPermissionId(Long permissionId);
}
