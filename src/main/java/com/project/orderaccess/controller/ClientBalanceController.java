package com.project.orderaccess.controller;

import com.project.orderaccess.exception.ExceptionResponse;
import com.project.orderaccess.service.clientbalance.ClientBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-balances")
@RequiredArgsConstructor
public class ClientBalanceController {

    private final ClientBalanceService clientBalanceService;

    @PutMapping("/refresh-all")
    @Operation(summary = "Refresh profit for all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profit refreshed"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))

    })
    public void refreshProfitForAllClients() {
        clientBalanceService.refreshProfitForAllClients();
    }

}
