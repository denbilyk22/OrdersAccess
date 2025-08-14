package com.project.orderaccess.controller;

import com.project.orderaccess.dto.response.OrderProcessingResult;
import com.project.orderaccess.exception.ExceptionResponse;
import com.project.orderaccess.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/identical")
    @Operation(summary = "Imitate several number of identical orders with price of 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process successful", content = @Content(schema = @Schema(implementation = OrderProcessingResult.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public OrderProcessingResult imitateIdenticalOrdersWithSamePrice(@RequestParam int number) {
        return orderService.imitateIdenticalOrdersWithSamePrice(number);
    }

    @PostMapping("/decreasing-price")
    @Operation(summary = "Imitate 10 orders creation with decreasing price from 100")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process successful", content = @Content(schema = @Schema(implementation = OrderProcessingResult.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public OrderProcessingResult imitateTenOrdersWithDecreasingPrice() {
        return orderService.imitateTenOrdersWithDecreasingPrice();
    }

    @PostMapping("/client-deactivation")
    @Operation(summary = "Imitate several number of different orders with client deactivation between any orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process successful", content = @Content(schema = @Schema(implementation = OrderProcessingResult.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public OrderProcessingResult imitateOrdersWithClientDeactivation(@RequestParam int number) {
        return orderService.imitateOrdersWithClientDeactivation(number);
    }

}
