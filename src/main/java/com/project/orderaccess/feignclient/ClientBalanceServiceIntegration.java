package com.project.orderaccess.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "client-balance-service", url = "${urls.orders-api}/client-balances")
public interface ClientBalanceServiceIntegration {

    @PutMapping("/refresh-all")
    void refreshProfitForAllClients();

}
