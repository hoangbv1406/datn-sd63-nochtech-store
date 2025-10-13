package com.project.shopapp.controllers;

import com.project.shopapp.components.SecurityUtils;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.order.OrderResponse;
import com.project.shopapp.services.orders.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SecurityUtils securityUtils;

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseObject> getOrder(@PathVariable("orderId") Long orderId) {
        Order existingOrder = orderService.getOrderById(orderId);
        OrderResponse orderResponse = OrderResponse.fromOrder(existingOrder);
        return ResponseEntity.ok(new ResponseObject(
                "Order retrieved successfully. orderId = " + orderId,
                HttpStatus.OK,
                orderResponse
        ));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .message(String.join(";", errorMessages))
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
            );
        }
        User loginUser = securityUtils.getLoggedInUser();
        if (orderDTO.getUserId() == null) {
            orderDTO.setUserId(loginUser.getId());
        }
        Order orderResponse = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Order created successfully.")
                .data(orderResponse)
                .status(HttpStatus.OK)
                .build()
        );
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseObject> updateOrder(
            @Valid @PathVariable("orderId") Long orderId,
            @Valid @RequestBody OrderDTO orderDTO
    ) throws Exception {
        Order order = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.ok(new ResponseObject(
                "Order updated successfully. orderId = " + orderId,
                HttpStatus.OK,
                order
        ));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order deleted successfully. orderId = " + orderId);
    }

    @GetMapping("/get-orders-by-keyword")
    public ResponseEntity<String> getOrdersByKeyword() {
        return ResponseEntity.ok("Orders retrieved successfully.");
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
