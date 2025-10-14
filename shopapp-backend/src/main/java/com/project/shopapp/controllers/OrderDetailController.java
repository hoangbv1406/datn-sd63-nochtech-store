package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.responses.order.OrderDetailResponse;
import com.project.shopapp.services.orderdetails.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("orderDetailId") Long orderDetailId) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(orderDetailId);
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(orderDetail);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Order detail retrieved successfully. orderDetailId = " + orderDetailId)
                .status(HttpStatus.OK)
                .data(orderDetailResponse)
                .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(newOrderDetail);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Order detail created successfully.")
                .status(HttpStatus.CREATED)
                .data(orderDetailResponse)
                .build()
        );
    }

    @PutMapping("/{orderDetailId}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable("orderDetailId") Long orderDetailId) {
        return ResponseEntity.ok("Order detail updated successfully. orderDetailId = " + orderDetailId);
    }

    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable("orderDetailId") Long orderDetailId) {
        return ResponseEntity.ok("Order detail deleted successfully. orderDetailId = " + orderDetailId);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<String> getOrderDetails(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("Order details retrieved successfully. orderId = " + orderId);
    }

}
