package com.project.orderaccess.service.order;

import com.project.orderaccess.dto.response.OrderProcessingResult;
import com.project.orderaccess.feignclient.ClientServiceIntegration;
import com.project.orderaccess.feignclient.OrderServiceIntegration;
import com.project.orderaccess.feignclient.dto.request.ClientRequest;
import com.project.orderaccess.feignclient.dto.request.OrderRequest;
import com.project.orderaccess.feignclient.dto.response.ClientResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ClientServiceIntegration clientServiceIntegration;
    private final OrderServiceIntegration orderServiceIntegration;

    private static final RandomStringUtils RANDOM_STRING_GENERATOR = RandomStringUtils.secure();
    private static final RandomUtils RANDOM_NUMBER_GENERATOR = RandomUtils.secure();

    @Override
    public OrderProcessingResult imitateIdenticalOrdersWithSamePrice(int numberOfOrders) {
        // create supplier and consumer
        var supplier = createClient("Supplier");
        var consumer = createClient("Consumer");

        // create counters
        var succeeds = new AtomicInteger(0);
        var errors = new AtomicInteger(0);

        // create and process order requests
        var orders = createIdenticalOrders(numberOfOrders, supplier.id(), consumer.id());

        orders.parallelStream().forEach(order -> {
            try {
                orderServiceIntegration.createOrder(order);
                succeeds.incrementAndGet();
            } catch (FeignException e) {
                log.error("Order creation failed due to: ", e);
                errors.incrementAndGet();
            }
        });

        return OrderProcessingResult.builder()
                .numberOfSucceeds(succeeds.get())
                .numberOfErrors(errors.get())
                .build();
    }

    @Override
    public OrderProcessingResult imitateTenOrdersWithDecreasingPrice() {
        // create supplier and consumer
        var supplier = createClient("Supplier");
        var consumer = createClient("Consumer");

        // create order for 970 profit
        var initialOrderRequest = OrderRequest.builder()
                .name(RANDOM_STRING_GENERATOR.nextAlphabetic(9))
                .price(BigDecimal.valueOf(970))
                .supplierId(supplier.id())
                .consumerId(consumer.id())
                .build();

        orderServiceIntegration.createOrder(initialOrderRequest);

        // create counters
        var succeeds = new AtomicInteger(0);
        var errors = new AtomicInteger(0);

        // create and process order requests
        var orders = createTenOrdersWithDecreasingPriceFromHundred(supplier.id(), consumer.id());

        orders.parallelStream().forEach(orderRequest -> {
            try {
                orderServiceIntegration.createOrder(orderRequest);
                succeeds.incrementAndGet();
            } catch (FeignException e) {
                log.error("Order creation failed due to: ", e);
                errors.incrementAndGet();
            }
        });

        return OrderProcessingResult.builder()
                .numberOfSucceeds(succeeds.get())
                .numberOfErrors(errors.get())
                .build();
    }

    @Override
    public OrderProcessingResult imitateOrdersWithClientDeactivation(int numberOfOrders) {
        // create supplier and consumer
        var supplier = createClient("Supplier");
        var consumer = createClient("Consumer");

        // create counters
        var succeeds = new AtomicInteger(0);
        var errors = new AtomicInteger(0);

        // create order requests
        var orders = createDifferentOrders(numberOfOrders, supplier.id(), consumer.id());

        // create not started completable futures for order requests
        var futures = orders.stream()
                .map(orderRequest -> (Supplier<CompletableFuture<Void>>) () -> CompletableFuture.runAsync(() -> {
                    try {
                        orderServiceIntegration.createOrder(orderRequest);
                        succeeds.incrementAndGet();
                    } catch (FeignException e) {
                        log.error("Order creation failed due to: ", e);
                        errors.incrementAndGet();
                    }
                }))
                .collect(Collectors.toList());

        // add completable future for client deactivation
        futures.add(() -> CompletableFuture.runAsync(() -> {
            try {
                clientServiceIntegration.setActiveToClient(consumer.id(), false);
            } catch (FeignException e) {
                log.error("Client deactivation failed due to: ", e);
            }
        }));

        // mix the futures list
        Collections.shuffle(futures, new Random(System.currentTimeMillis()));

        // start all futures
        var startedFutures = futures.parallelStream()
                .map(Supplier::get)
                .toList();

        CompletableFuture.allOf(startedFutures.toArray(new CompletableFuture[0])).join();

        return OrderProcessingResult.builder()
                .numberOfSucceeds(succeeds.get())
                .numberOfErrors(errors.get())
                .build();
    }

    private List<OrderRequest> createIdenticalOrders(int number, UUID supplierId, UUID consumerId) {
        var orders = new ArrayList<OrderRequest>();

        for (int i = 0; i < number; i++) {
            orders.add(OrderRequest.builder()
                    .price(BigDecimal.ONE)
                    .name("Order")
                    .supplierId(supplierId)
                    .consumerId(consumerId)
                    .build());
        }

        return orders;
    }

    private List<OrderRequest> createTenOrdersWithDecreasingPriceFromHundred(UUID supplierId, UUID consumerId) {
        var orders = new ArrayList<OrderRequest>();

        var hundred = BigDecimal.valueOf(100);

        for (int i = 1; i <= 10; i++) {
            var price = hundred.subtract(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(i)));

            orders.add(OrderRequest.builder()
                    .price(price)
                    .name(RANDOM_STRING_GENERATOR.nextAlphabetic(9))
                    .supplierId(supplierId)
                    .consumerId(consumerId)
                    .build());
        }

        return orders;
    }

    private List<OrderRequest> createDifferentOrders(int number, UUID supplierId, UUID consumerId) {
        var orders = new ArrayList<OrderRequest>();

        for (int i = 0; i < number; i++) {
            var price = RANDOM_NUMBER_GENERATOR.randomInt(1, 30);

            orders.add(OrderRequest.builder()
                    .price(BigDecimal.valueOf(price))
                    .name(RANDOM_STRING_GENERATOR.nextAlphabetic(9))
                    .supplierId(supplierId)
                    .consumerId(consumerId)
                    .build());
        }

        return orders;
    }

    private ClientResponse createClient(String name) {
        var email = "%s@email.com".formatted(RANDOM_STRING_GENERATOR.nextAlphabetic(9));

        var clientRequest = ClientRequest.builder()
                .name(name)
                .email(email)
                .address(RANDOM_STRING_GENERATOR.nextAlphabetic(9))
                .build();

        return clientServiceIntegration.createClient(clientRequest);
    }
}
