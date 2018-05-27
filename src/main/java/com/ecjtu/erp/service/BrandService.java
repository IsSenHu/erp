package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.Brand;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 10:29 2018/5/13
 * @Modified By:
 */
public interface BrandService extends BaseService {
    List<Brand> findAll();

    JsonResult saveBrand(Brand brand);

    Brand findBrandById(Integer brandId);

    JsonResult deleteBrandById(Integer brandId);
}
