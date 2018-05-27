package com.ecjtu.erp.service;

import com.ecjtu.erp.model.custom.Customer;
import com.ecjtu.erp.util.JsonResult;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:03 2018/5/14
 * @Modified By:
 */
public interface CustomerService extends BaseService {
    List<Customer> search(Customer customer);

    JsonResult saveCustomer(Customer customer);

    JsonResult deleteCustomer(Long customerId);

    Customer findCustomerById(Long customerId);
}
