package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.billdetail.BillDetail;
import com.ecjtu.erp.service.BillDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author: HuSen
 * @Description:
 * @Date: Created in 20:28 2018/5/14
 * @Modified By:
 */
@RestController
public class BillDetailController {

    @Autowired
    private BillDetailService billDetailService;

    @GetMapping("/billDetails")
    private ModelAndView billDetails(Integer flag){
        List<BillDetail> list = billDetailService.search(flag);
        return new ModelAndView("pages/tables/billDetails")
                .addObject("list", list)
                .addObject("menusShow", billDetailService.menusAndChilds());
    }

}
