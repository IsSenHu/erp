package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 9:14 2018/5/15
 * @Modified By:
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllBySupplierGoodIdAndOrderId(Long supplierGoodId, Long orderId);

    List<Item> findAllByOrderId(Long orderId);
}
