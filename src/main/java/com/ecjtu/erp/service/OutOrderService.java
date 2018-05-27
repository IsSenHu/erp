package com.ecjtu.erp.service;

import com.ecjtu.erp.model.order.OutOrder;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:07 2018/5/15
 * @Modified By:
 */
public interface OutOrderService extends BaseService {
    List<OutOrder> search(OutOrder outOrder);

    JsonResult outStock(Long outOrderId);
}
