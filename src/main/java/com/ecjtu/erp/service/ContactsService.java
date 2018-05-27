package com.ecjtu.erp.service;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;
import com.ecjtu.erp.model.supplier.vo.ContactsVo;
import com.ecjtu.erp.model.vo.contacts.ReturnVo;
import com.ecjtu.erp.util.JsonResult;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:42 2018/5/6
 * @Modified By:
 */
public interface ContactsService extends BaseService {
    JsonResult saveContacts(Contacts contacts);

    List<ContactsVo> search(Contacts contacts);

    JsonResult deleteContacts(Long contactsId);

    ReturnVo<Supplier, Contacts> findContactsById(Long contactsId);
}
