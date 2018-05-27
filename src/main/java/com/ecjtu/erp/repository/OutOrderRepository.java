package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.order.OutOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:29 2018/5/15
 * @Modified By:
 */
@Repository
public interface OutOrderRepository extends JpaRepository<OutOrder, Long>, JpaSpecificationExecutor<OutOrder> {
}
