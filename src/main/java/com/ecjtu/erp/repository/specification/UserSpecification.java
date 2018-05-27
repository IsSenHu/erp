package com.ecjtu.erp.repository.specification;

import com.ecjtu.erp.model.User;
import com.ecjtu.erp.model.vo.SearchUserVO;
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
 * @Date: Created in 17:07 2018/4/30
 * @Modified By:
 */
public class UserSpecification implements Specification<User> {
    private SearchUserVO searchUserVO;

    public UserSpecification(SearchUserVO searchUserVO) {
        super();
        this.searchUserVO = searchUserVO;
    }

    @Nullable
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!Objects.isNull(searchUserVO)){
            //用户名 like
            if(StringUtils.isNotBlank(searchUserVO.getUsername())){
                predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + searchUserVO.getUsername().trim() + "%"));
            }
            //姓名like
            if(StringUtils.isNotBlank(searchUserVO.getName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + searchUserVO.getName() + "%"));
            }
            //手机号 like
            if(StringUtils.isNotBlank(searchUserVO.getPhone())){
                predicates.add(criteriaBuilder.like(root.get("phone").as(String.class), "%" + searchUserVO.getPhone() + "%"));
            }
        }
        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    }
}
