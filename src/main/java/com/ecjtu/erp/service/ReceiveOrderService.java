package com.ecjtu.erp.service;

import com.ecjtu.erp.model.order.ReceiveOrder;
import com.ecjtu.erp.model.order.vo.ReceiveOrderVo;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:19 2018/5/14
 * @Modified By:
 */
public interface ReceiveOrderService extends BaseService {
    List<ReceiveOrderVo> search(ReceiveOrder receiveOrder);

    JsonResult receivedMoney(Long receiveOrderId);
}
