package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.Type;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 13:14 2018/5/13
 * @Modified By:
 */
public interface TypeService extends BaseService {
    List<Type> findAll();

    JsonResult saveType(Type type);

    Type findTypeById(Integer typeId);

    JsonResult deleteTypeById(Integer typeId);
}
