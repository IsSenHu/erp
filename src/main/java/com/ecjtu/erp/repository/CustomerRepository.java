package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.custom.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:04 2018/5/14
 * @Modified By:
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
