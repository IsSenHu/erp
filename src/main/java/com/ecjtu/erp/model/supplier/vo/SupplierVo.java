package com.ecjtu.erp.model.supplier.vo;

import com.ecjtu.erp.model.supplier.Contacts;
import com.ecjtu.erp.model.supplier.Supplier;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 19:03 2018/5/6
 * @Modified By:
 */
public class SupplierVo extends Supplier {
    private Contacts contacts;

    private String contactsName;

    private Long contactsId;

    public Long getContactsId() {
        return contactsId;
    }

    public void setContactsId(Long contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }
}
