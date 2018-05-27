package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.supplier.SupplierGood;
import com.ecjtu.erp.model.supplier.vo.SupplierGoodVo;
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
 * @Date: Created in 16:09 2018/5/12
 * @Modified By:
 */
public class SupplierGoodSpecification implements Specification<SupplierGood> {
    private SupplierGood supplierGood;

    public SupplierGoodSpecification(SupplierGood supplierGood) {
        super();
        this.supplierGood = supplierGood;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<SupplierGood> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(supplierGood)){
            //商品名称
            if(StringUtils.isNotBlank(supplierGood.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + supplierGood.getName() + "%"));
            }
            //供应商编码
            if(!Objects.isNull(supplierGood.getSupplierId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierId"), supplierGood.getSupplierId()));
            }
            //商品编码
            if(!Objects.isNull(supplierGood.getSupplierGoodId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierGoodId"), supplierGood.getSupplierGoodId()));
            }
            //商品状态
            if(!Objects.isNull(supplierGood.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), supplierGood.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
