package com.project.shopapp.services.orderdetails;

import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.OrderDetail;

public interface OrderDetailService {
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
}
