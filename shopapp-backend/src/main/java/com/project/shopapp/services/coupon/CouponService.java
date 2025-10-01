package com.project.shopapp.services.coupon;

import com.project.shopapp.repositories.CouponConditionRepository;
import com.project.shopapp.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService implements CouponServiceImpl {
    private final CouponRepository couponRepository;
    private final CouponConditionRepository couponConditionRepository;
}
