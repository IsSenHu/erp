package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.Specification;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 8:39 2018/5/13
 * @Modified By:
 */
public interface SpecificationService extends BaseService {
    List<Specification> findAll();

    JsonResult saveSpecification(Specification specification);

    Specification findSpecificationById(Integer specificationId);

    JsonResult deleteSpecificationById(Integer specificationId);
}
