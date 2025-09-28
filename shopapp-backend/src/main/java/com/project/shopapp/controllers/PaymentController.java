package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payments")
public class PaymentController {

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
