package com.ecjtu.erp.service;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.purchase.vo.PurchaseOrderVo;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:41 2018/5/14
 * @Modified By:
 */
public interface PurchaseService extends BaseService {
    JsonResult createPurchaseOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrderVo> search(PurchaseOrderVo vo);

    JsonResult canOrNotCanPurchase(Long purchaseOrderId, Integer flag);

    PurchaseOrderVo findPurchaseOrderById(Long purchaseOrderId);

    JsonResult purchasing(Long purchaseOrderId, User user);
}
