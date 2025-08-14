package com.project.orderaccess.feignclient;

import com.project.orderaccess.feignclient.dto.request.OrderRequest;
import com.project.orderaccess.feignclient.dto.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service", url = "${urls.orders-api}/orders")
public interface OrderServiceIntegration {

    @PostMapping
    OrderResponse createOrder(OrderRequest orderRequest);

}
