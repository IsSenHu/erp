package com.ecjtu.erp.service;

import com.ecjtu.erp.model.purchase.InOrder;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 16:12 2018/5/14
 * @Modified By:
 */
public interface InOrderService extends BaseService {
    List<InOrder> search(InOrder inOrder);

    JsonResult saveInOrder(InOrder inOrder);

    JsonResult setInOrderStatus(Long inOrderId, Integer flag);
}
