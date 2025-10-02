package com.project.shopapp.controllers;

import com.project.shopapp.services.vnpay.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final VNPayService vnPayService;

    @PostMapping("/create-payment-url")
    public ResponseEntity<String> createPayment() {
        return ResponseEntity.ok("Payment URL created successfully.");
    }

    @PostMapping("/query")
    public ResponseEntity<String> queryTransaction() {
        return ResponseEntity.ok("Transaction queried successfully.");
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refundTransaction() {
        return ResponseEntity.ok("Transaction refunded successfully.");
    }

}
