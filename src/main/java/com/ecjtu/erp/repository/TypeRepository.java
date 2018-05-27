package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 13:15 2018/5/13
 * @Modified By:
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
}
