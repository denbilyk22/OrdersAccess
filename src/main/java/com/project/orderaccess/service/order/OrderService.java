package com.project.orderaccess.service.order;

import com.project.orderaccess.dto.response.OrderProcessingResult;

public interface OrderService {
    OrderProcessingResult imitateIdenticalOrdersWithSamePrice(int numberOfOrders);
    OrderProcessingResult imitateTenOrdersWithDecreasingPrice();
    OrderProcessingResult imitateOrdersWithClientDeactivation(int numberOfOrders);
}
