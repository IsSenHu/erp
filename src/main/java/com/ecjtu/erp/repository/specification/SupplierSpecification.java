package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.supplier.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 胡森
 * @Description:
 * @Date: Created in 19:05 2018/5/6
 * @Modified By:
 */
public class SupplierSpecification implements Specification<Supplier> {

    private Supplier supplier;

    public SupplierSpecification(Supplier supplier) {
        super();
        this.supplier = supplier;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(supplier)){
            //供应商编号
            if(!Objects.isNull(supplier.getSupplierId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierId"), supplier.getSupplierId()));
            }
            //供应商名称
            if(StringUtils.isNotBlank(supplier.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + supplier.getName() + "%"));
            }
            //供应商状态
            if(!Objects.isNull(supplier.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), supplier.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
