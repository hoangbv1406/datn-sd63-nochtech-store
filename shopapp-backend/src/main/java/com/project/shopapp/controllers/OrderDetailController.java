package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-details")
public class OrderDetailController {

    @PostMapping("")
    public ResponseEntity<String> createOrderDetail() {
        return ResponseEntity.ok("Order detail created successfully.");
    }

    @PutMapping("/{orderDetailId}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable("orderDetailId") Long orderDetailId) {
        return ResponseEntity.ok("Order detail updated successfully. orderDetailId = " + orderDetailId);
    }

    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable("orderDetailId") Long orderDetailId) {
        return ResponseEntity.ok("Order detail deleted successfully. orderDetailId = " + orderDetailId);
    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<String> getOrderDetail(@PathVariable("orderDetailId") Long orderDetailId) {
        return ResponseEntity.ok("Order detail retrieved successfully. orderDetailId = " + orderDetailId);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<String> getOrderDetails(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order details retrieved successfully. orderId = " + orderId);
    }

}
