package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.purchase.PayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 14:54 2018/5/14
 * @Modified By:
 */
@Repository
public interface PayOrderRepository extends JpaRepository<PayOrder, Long>, JpaSpecificationExecutor<PayOrder> {
    List<PayOrder> findAllByStatus(Integer status);
}
