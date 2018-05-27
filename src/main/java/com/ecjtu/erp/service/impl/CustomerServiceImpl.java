package com.ecjtu.erp.service.impl;

import com.ecjtu.erp.model.custom.Customer;
import com.ecjtu.erp.repository.CustomerRepository;
import com.ecjtu.erp.repository.specification.CustomerSpecification;
import com.ecjtu.erp.service.CustomerService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:03 2018/5/14
 * @Modified By:
 */
@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> search(Customer customer) {
        return customerRepository.findAll(new CustomerSpecification(customer));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult saveCustomer(Customer customer) {
        if(Objects.isNull(customer)){
            return new JsonResult(500);
        }
        try {
            if(Objects.isNull(customer.getCustomerId())){
                log.info("新增客户:{}", customer);
                customerRepository.save(customer);
            }else {
                log.info("修改客户:{}", customer);
                Customer old = customerRepository.findById(customer.getCustomerId()).get();
                BeanUtils.copyProperties(customer, old);
                customerRepository.save(old);
            }
            return new JsonResult(200);
        }catch (Exception e){
            log.error("保存客户失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public JsonResult deleteCustomer(Long customerId) {
        if(Objects.isNull(customerId)){
            return new JsonResult(400);
        }
        try {
            customerRepository.deleteById(customerId);
            return new JsonResult(200);
        }catch (Exception e){
            log.error("删除客户失败，发生异常:{}", e.getMessage());
            e.printStackTrace();
            return new JsonResult(500);
        }
    }

    @Override
    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).get();
    }
}
