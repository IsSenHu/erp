package com.ecjtu.erp.service;

import com.ecjtu.erp.model.good.NewGood;
import com.ecjtu.erp.util.JsonResult;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 21:33 2018/5/6
 * @Modified By:
 */
public interface NewGoodService extends BaseService {
    JsonResult saveNewGood(NewGood newGood);

    List<NewGood> search(NewGood newGood);

    NewGood findNewGoodById(Long materialId);

    JsonResult deleteNewGoodById(Long materialId);

    JsonResult add2WaitReview(Long materialId);

    JsonResult checkNewGood(Long materialId, String flag);

    JsonResult add2WaitCheck(Long materialId);

    JsonResult productOrNotProduct(Long materialId, String flag);
}
