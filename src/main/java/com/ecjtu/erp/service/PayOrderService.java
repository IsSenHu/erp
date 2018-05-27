package com.ecjtu.erp.service;

import com.ecjtu.erp.model.purchase.PayOrder;
import com.ecjtu.erp.model.purchase.vo.PayOrderVo;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 15:05 2018/5/14
 * @Modified By:
 */
public interface PayOrderService extends BaseService {
    List<PayOrderVo> search(PayOrder payOrder);

    List<PayOrder> findAllNoFinishPayOrder();
}
