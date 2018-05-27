package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.purchase.PurchaseOrder;
import com.ecjtu.erp.model.purchase.vo.PurchaseOrderVo;
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
 * @Author: HuSen
 * @Description:
 * @Date: Created in 12:21 2018/5/14
 * @Modified By:
 */
public class PurchaseOrderSpecification implements Specification<PurchaseOrder> {
    private PurchaseOrderVo purchaseOrderVo;

    public PurchaseOrderSpecification(PurchaseOrderVo purchaseOrderVo) {
        super();
        this.purchaseOrderVo = purchaseOrderVo;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<PurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(purchaseOrderVo)){
            //采购单编号
            if(!Objects.isNull(purchaseOrderVo.getPurchaseOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("purchaseOrderId"), purchaseOrderVo.getPurchaseOrderId()));
            }
            //采购单状态
            if(!Objects.isNull(purchaseOrderVo.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), purchaseOrderVo.getStatus()));
            }
            //供应商编号
            if(!Objects.isNull(purchaseOrderVo.getSupplier()) && !Objects.isNull(purchaseOrderVo.getSupplier().getSupplierId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierId"), purchaseOrderVo.getSupplier().getSupplierId()));
            }
            //商品编号
            if(!Objects.isNull(purchaseOrderVo.getGood()) && !Objects.isNull(purchaseOrderVo.getGood().getSupplierGoodId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("supplierGoodId"), purchaseOrderVo.getGood().getSupplierGoodId()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
