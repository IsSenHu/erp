package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.NewGood;
import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodNeed;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodVo;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 16:01 2018/5/12
 * @Modified By:
 */
public interface SupplierGoodService extends BaseService{

    NewGood findNewGoodById(Long newGoodId);

    SupplierGoodNeed getAllCreateNeed();

    JsonResult saveSupplierGood(SupplierGood supplierGood);

    List<SupplierGood> findAll(SupplierGood supplierGood);

    SupplierGoodVo findSupplierGoodById(Long supplierGoodId);

    JsonResult canOrNotCanSupply(Long supplierGoodId, Integer flag);

    List<SupplierGood> supplierGoodList();

    SupplierGood findSupplierGoodById2(Long supplierGoodId);
}
