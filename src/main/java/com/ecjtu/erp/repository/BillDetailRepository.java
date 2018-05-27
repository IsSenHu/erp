package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.billdetail.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:32 2018/5/14
 * @Modified By:
 */
@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findAllByOccurType(Integer occurType);
}
