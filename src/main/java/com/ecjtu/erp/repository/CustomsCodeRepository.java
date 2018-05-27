package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.CustomsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:18 2018/5/13
 * @Modified By:
 */
@Repository
public interface CustomsCodeRepository extends JpaRepository<CustomsCode, Long> {
}
