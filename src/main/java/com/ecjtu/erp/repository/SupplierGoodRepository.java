package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.supplier.SupplierGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:02 2018/5/12s
 * @Modified By:
 */
@Repository
public interface SupplierGoodRepository extends JpaRepository<SupplierGood, Long>, JpaSpecificationExecutor<SupplierGood> {
    List<SupplierGood> findAllByStatus(Integer status);
}
