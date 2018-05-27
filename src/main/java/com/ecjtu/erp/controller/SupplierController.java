package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.SupplierVo;
import com.ecjtu.erp.service.SupplierService;
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
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:28 2018/5/6
 * @Modified By:
 */
@RestController
public class SupplierController {
    private static final Logger log = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    /**
     * 查询所有的供应商
     * @return
     */
    @PostMapping("/findAllSupplier")
    private List<Supplier> findAllSupplier(){
        return supplierService.findAllSupplier();
    }

    /**
     * 供应商管理页面
     * @return
     */
    @GetMapping("/suppliers")
    private ModelAndView suppliers(Supplier supplier){
        List<SupplierVo> list = supplierService.search(supplier);
        return new ModelAndView("pages/tables/suppliers")
                .addObject("list", list)
                .addObject("menusShow", supplierService.menusAndChilds());
    }

    /**
     * 保存供应商
     * @param supplier
     * @param result
     * @return
     */
    @PostMapping("/saveSupplier")
    private JsonResult saveSupplier(@Valid @RequestBody Supplier supplier, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return supplierService.saveSupplier(supplier);
    }

    @PostMapping("/findSupplierById")
    private Supplier findSupplierById(Long supplierId){
        return supplierService.findSupplierById(supplierId);
    }

    @PostMapping("/deleteSupplier")
    private JsonResult deleteSupplier(Long supplierId){
        return supplierService.deleteSupplier(supplierId);
    }

    @PostMapping("/accessSupplierOrNot")
    private JsonResult accessSupplierOrNot(Long supplierId, String flag){
        return supplierService.accessSupplierOrNot(supplierId, flag);
    }

    /**
     * 设置账期时间
     * @param supplier
     * @return
     */
    @PostMapping("/setAccount")
    private JsonResult setAccount(@RequestBody Supplier supplier){
        return supplierService.setAccount(supplier);
    }
}
