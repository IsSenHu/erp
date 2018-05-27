package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 8:40 2018/5/13
 * @Modified By:
 */
@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer> {
}
