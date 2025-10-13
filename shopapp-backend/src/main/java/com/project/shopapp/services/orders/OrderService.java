package com.project.shopapp.services.orders;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.order.OrderResponse;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();
    Order getOrderById(Long orderId);
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    Order updateOrderStatus(Long id, String status) throws DataNotFoundException;
    void deleteOrder(Long orderId);
    List<OrderResponse> findByUserId(Long userId);
}
