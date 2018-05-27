package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:30 2018/5/6
 * @Modified By:
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier>{
}
