package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.order.ReceiveOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:20 2018/5/14
 * @Modified By:
 */
@Repository
public interface ReceiveOrderRepository extends JpaRepository<ReceiveOrder, Long>, JpaSpecificationExecutor<ReceiveOrder> {
}
