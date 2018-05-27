package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.purchase.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:42 2018/5/14
 * @Modified By:
 */
@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {
}
