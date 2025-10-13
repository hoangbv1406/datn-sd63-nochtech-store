package com.project.shopapp.services.orders;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
}
