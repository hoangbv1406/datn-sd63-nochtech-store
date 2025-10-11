package com.project.shopapp.services.coupon;

public interface CouponService {
    double calculateCouponValue(String couponCode, double totalAmount);
}
