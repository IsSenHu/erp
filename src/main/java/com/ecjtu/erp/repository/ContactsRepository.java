package com.ecjtu.erp.repository;

import com.ecjtu.erp.model.supplier.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 14:44 2018/5/6
 * @Modified By:
 */
@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long>, JpaSpecificationExecutor<Contacts> {
    List<Contacts> findAllBySupplierId(Long supplierId);
}
