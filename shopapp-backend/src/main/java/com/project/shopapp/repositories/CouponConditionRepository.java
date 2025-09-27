package com.project.shopapp.repositories;

import com.project.shopapp.models.CouponCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponConditionRepository extends JpaRepository<CouponCondition, Long> {
}
