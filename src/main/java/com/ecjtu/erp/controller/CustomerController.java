package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.custom.Customer;
import com.ecjtu.erp.service.CustomerService;
import com.ecjtu.erp.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:02 2018/5/14
 * @Modified By:
 */
@RestController
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    /**
     * 客户页面
     * @param customer
     * @return
     */
    @GetMapping("/customers")
    private ModelAndView customers(Customer customer){
        List<Customer> list = customerService.search(customer);
        return new ModelAndView("pages/tables/customers")
                .addObject("list", list)
                .addObject("menusShow", customerService.menusAndChilds());
    }

    /**
     * 保存客户
     * @param customer
     * @param result
     * @return
     */
    @PostMapping("/saveCustomer")
    private JsonResult saveCustomer(@RequestBody @Valid Customer customer, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return customerService.saveCustomer(customer);
    }

    /**
     * 删除客户
     * @param customerId
     * @return
     */
    @PostMapping("/deleteCustomer")
    private JsonResult deleteCustomer(Long customerId){
        return customerService.deleteCustomer(customerId);
    }

    /**
     * 根据id查询客户
     * @param customerId
     * @return
     */
    @PostMapping("/findCustomerById")
    private Customer findCustomerById(Long customerId){
        return customerService.findCustomerById(customerId);
    }
}
