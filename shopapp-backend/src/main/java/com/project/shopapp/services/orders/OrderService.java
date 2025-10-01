package com.project.shopapp.services.orders;

import com.project.shopapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceImpl {
    private final OrderRepository orderRepository;
}
