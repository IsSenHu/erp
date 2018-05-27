package com.ecjtu.erp.service;

import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.SupplierVo;
import com.ecjtu.erp.util.JsonResult;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:29 2018/5/6
 * @Modified By:
 */
public interface SupplierService extends BaseService {
    List<Supplier> findAllSupplier();

    JsonResult saveSupplier(Supplier supplier);

    List<SupplierVo> search(Supplier supplier);

    Supplier findSupplierById(Long supplierId);

    JsonResult deleteSupplier(Long supplierId);

    JsonResult accessSupplierOrNot(Long supplierId, String flag);

    JsonResult setAccount(Supplier supplier);
}
