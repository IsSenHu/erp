package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.good.StockKeepingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 9:56 2018/5/13
 * @Modified By:
 */
@Repository
public interface StockKeepingUnitRepository extends JpaRepository<StockKeepingUnit, Integer> {
}
