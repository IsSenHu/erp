package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.purchase.PayOrder;
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
 * @Date: Created in 15:17 2018/5/14
 * @Modified By:
 */
public class PayOrderSpecification implements Specification<PayOrder> {
    private PayOrder payOrder;

    public PayOrderSpecification(PayOrder payOrder) {
        super();
        this.payOrder = payOrder;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<PayOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(payOrder)){
            //付款单号
            if(!Objects.isNull(payOrder.getPayOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("payOrderId"), payOrder.getPayOrderId()));
            }
            //采购单编号
            if(!Objects.isNull(payOrder.getPurchaseOrderId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("purchaseOrderId"), payOrder.getPurchaseOrderId()));
            }
            //支付方式
            if(!Objects.isNull(payOrder.getPayType())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("payType"), payOrder.getPayType()));
            }
            //状态
            if(!Objects.isNull(payOrder.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), payOrder.getStatus()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
