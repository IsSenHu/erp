package com.ecjtu.erp.controller;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.ContactsVo;
import com.ecjtu.erp.model.vo.contacts.ReturnVo;
import com.ecjtu.erp.service.ContactsService;
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
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:11 2018/5/6
 * @Modified By:
 */
@RestController
public class ContactsController {
    private static final Logger log = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private ContactsService contactsService;


    /**
     * 联系人页面
     * @return
     */
    @GetMapping("/contacts")
    private ModelAndView contacts(Contacts contacts){
        List<ContactsVo> contactsList = contactsService.search(contacts);
        return new ModelAndView("pages/tables/contacts")
                .addObject("list", contactsList)
                .addObject("menusShow", contactsService.menusAndChilds());
    }

    /**
     * 保存联系人
     * @param contacts
     * @param result
     * @return
     */
    @PostMapping("/saveContacts")
    private JsonResult saveContacts(@Valid @RequestBody Contacts contacts, BindingResult result){
        if(result.hasErrors()){
            return new JsonResult(400, result.getFieldErrors());
        }
        return contactsService.saveContacts(contacts);
    }

    @PostMapping("/deleteContacts")
    private JsonResult deleteContacts(Long contactsId){
        if(Objects.isNull(contactsId)){
            return new JsonResult(400);
        }
        return contactsService.deleteContacts(contactsId);
    }

    @PostMapping("/findContactsById")
    private ReturnVo<Supplier, Contacts> findContactsById(Long contactsId){
        if(Objects.isNull(contactsId)){
            return null;
        }
        return contactsService.findContactsById(contactsId);
    }
}
