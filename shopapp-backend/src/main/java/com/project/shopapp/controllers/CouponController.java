package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/coupons")
public class CouponController {

    @GetMapping("/calculate")
    public ResponseEntity<String> calculateCouponValue(
            @RequestParam("couponCode") String couponCode,
            @RequestParam("totalAmount") Double totalAmount
    ) {
        return ResponseEntity.ok("Coupon calculated successfully.");
    }

}
