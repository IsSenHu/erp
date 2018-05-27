package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.good.NewGood;
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
 * @Date: Created in 23:32 2018/5/6
 * @Modified By:
 */
public class NewGoodSpecification implements Specification<NewGood> {
    private NewGood newGood;

    public NewGoodSpecification(NewGood newGood) {
        super();
        this.newGood = newGood;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<NewGood> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(!Objects.isNull(newGood)){
            //商品编号
            if(!Objects.isNull(newGood.getMaterialId())){
                predicates.add(criteriaBuilder.equal(root.<Long>get("materialId"), newGood.getMaterialId()));
            }
            //商品名称
            if(StringUtils.isNotBlank(newGood.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + newGood.getName() + "%"));
            }
            //商品状态
            if(!Objects.isNull(newGood.getStatus())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("status"), newGood.getStatus()));
            }
            //商品来源
            if(!Objects.isNull(newGood.getFromType())){
                predicates.add(criteriaBuilder.equal(root.<Integer>get("fromType"), newGood.getFromType()));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
