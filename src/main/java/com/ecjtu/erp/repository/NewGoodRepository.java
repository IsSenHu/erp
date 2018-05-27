package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.NewGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 21:35 2018/5/6
 * @Modified By:
 */
@Repository
public interface NewGoodRepository extends JpaRepository<NewGood, Long>, JpaSpecificationExecutor<NewGood> {
}
