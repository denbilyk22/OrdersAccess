package com.project.orderaccess.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "client-balance-service", url = "${urls.orders-api}/client-balance")
public interface ClientBalanceServiceIntegration {

    @PutMapping("/refresh-all")
    void refreshProfitForAllClients();

    @PutMapping("/{clientId}/refresh")
    void refreshProfitForClient(@PathVariable UUID clientId);

}
