package com.ecjtu.erp.service;

import com.ecjtu.erp.model.billdetail.BillDetail;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:29 2018/5/14
 * @Modified By:
 */
public interface BillDetailService extends BaseService {
    List<BillDetail> search(Integer flag);
}
