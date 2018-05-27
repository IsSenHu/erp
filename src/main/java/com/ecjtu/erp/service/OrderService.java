package com.ecjtu.erp.service;

import com.ecjtu.erp.model.order.Item;
import com.ecjtu.erp.model.order.Order;
import com.ecjtu.erp.model.order.vo.ItemVo;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:43 2018/5/14
 * @Modified By:
 */
public interface OrderService extends BaseService {

    JsonResult saveOrder(Order order);

    List<Order> search(Order order);

    JsonResult saveItem(Item item);

    List<ItemVo> findAllItemByOrderId(Long orderId);

    JsonResult setOrderStatus(Long orderId, Integer flag);
}
