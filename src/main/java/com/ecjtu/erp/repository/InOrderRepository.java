package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.purchase.InOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 16:13 2018/5/14
 * @Modified By:
 */
@Repository
public interface InOrderRepository extends JpaRepository<InOrder, Long>, JpaSpecificationExecutor<InOrder> {
}
