package com.ecjtu.erp.service;
import com.ecjtu.erp.model.good.CustomsCode;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 11:19 2018/5/13
 * @Modified By:
 */
public interface CustomsCodeService extends BaseService {
    List findAll();

    JsonResult saveCustomsCode(CustomsCode customsCode);

    CustomsCode findCustomsCodeById(Long id);

    JsonResult deleteCustomsCodeById(Long id);
}
