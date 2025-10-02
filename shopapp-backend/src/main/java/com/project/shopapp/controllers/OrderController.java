package com.project.shopapp.controllers;

import com.project.shopapp.services.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<String> createOrder() {
        return ResponseEntity.ok("Order created successfully.");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order updated successfully. orderId = " + orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order deleted successfully. orderId = " + orderId);
    }

    @GetMapping("/get-orders-by-keyword")
    public ResponseEntity<String> getOrdersByKeyword() {
        return ResponseEntity.ok("Orders retrieved successfully.");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order retrieved successfully. orderId = " + orderId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getOrders(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok("Orders for user retrieved successfully. userId = " + userId);
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order cancelled successfully. orderId = " + orderId);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") String status
    ) {
        return ResponseEntity.ok("Order status updated successfully.");
    }

}
