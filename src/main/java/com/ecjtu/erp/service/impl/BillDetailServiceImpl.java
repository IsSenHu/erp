package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.billdetail.BillDetail;
import com.ecjtu.erp.repository.BillDetailRepository;
import com.ecjtu.erp.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:29 2018/5/14
 * @Modified By:
 */
@Service
public class BillDetailServiceImpl extends BaseServiceImpl implements BillDetailService {

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<BillDetail> search(Integer flag) {
        return Objects.isNull(flag) ? billDetailRepository.findAll() : billDetailRepository.findAllByOccurType(flag);
    }
}
