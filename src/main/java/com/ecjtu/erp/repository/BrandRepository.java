package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:30 2018/5/13
 * @Modified By:
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
