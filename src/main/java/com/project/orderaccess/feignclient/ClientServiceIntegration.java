package com.project.orderaccess.feignclient;

import com.project.orderaccess.feignclient.dto.request.ClientRequest;
import com.project.orderaccess.feignclient.dto.response.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "client-service", url = "${urls.orders-api}/clients")
public interface ClientServiceIntegration {

    @PostMapping
    ClientResponse createClient(@RequestBody ClientRequest clientRequest);

    @PutMapping("/{clientId}/active")
    ClientResponse setActiveToClient(@PathVariable UUID clientId, @RequestParam boolean active);

}
