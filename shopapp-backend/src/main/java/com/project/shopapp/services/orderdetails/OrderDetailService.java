package com.project.shopapp.services.orderdetails;

import com.project.shopapp.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements OrderDetailServiceImpl {
    private final OrderDetailRepository orderDetailRepository;
}
