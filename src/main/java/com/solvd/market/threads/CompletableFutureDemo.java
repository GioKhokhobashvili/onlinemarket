package com.solvd.market.threads;

import com.solvd.market.market.Market;
import com.solvd.market.order.Order;
import com.solvd.market.order.OrderItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureDemo {

    private static final Logger LOGGER = LogManager.getLogger(CompletableFutureDemo.class);

    public static void runCompletableFutures(Market market) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> orderCountFuture = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("[CF-1] Counting orders...");
            return market.getOrders().size();
        }).thenApply(count -> {
            LOGGER.info("[CF-1] Order count fetched: {}", count);
            return count;
        });

        CompletableFuture<BigDecimal> totalRevenueFuture = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("[CF-2] Calculating total revenue...");
            return market.getOrders().stream()
                    .flatMap(o -> o.getOrderItems().stream())
                    .map(item -> item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        });

        CompletableFuture<List<String>> customerNamesFuture = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("[CF-3] Collecting customer names...");
            return market.getOrders().stream()
                    .map(o -> o.getCustomer().getName())
                    .distinct()
                    .collect(Collectors.toList());
        }).thenApplyAsync(names -> {
            LOGGER.info("[CF-3] Transforming names to uppercase...");
            return names.stream().map(String::toUpperCase).collect(Collectors.toList());
        });

        CompletableFuture<String> marketSummaryFuture = orderCountFuture
                .thenCombine(totalRevenueFuture, (count, revenue) -> {
                    String summary = "Market: " + market.getName() + " | Orders: " + count + " | Revenue: $" + revenue;
                    LOGGER.info("[CF-4] Combined summary: {}", summary);
                    return summary;
                });

        CompletableFuture<Void> notificationFuture = CompletableFuture.runAsync(() -> {
            LOGGER.info("[CF-5] Sending notifications to all customers...");
            market.getOrders().forEach(order ->
                    LOGGER.info("[CF-5] Notified: {}", order.getCustomer().getContactInfo().getEmail()));
        });

        CompletableFuture<Order> firstPendingOrderFuture = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("[CF-6] Finding first PENDING order...");
            return market.getOrders().stream()
                    .filter(o -> o.getOrderStatus().name().equals("PENDING"))
                    .findFirst()
                    .orElse(null);
        }).exceptionally(ex -> {
            LOGGER.error("[CF-6] Error finding pending order: {}", ex.getMessage());
            return null;
        });

        CompletableFuture.allOf(
                orderCountFuture,
                totalRevenueFuture,
                customerNamesFuture,
                marketSummaryFuture,
                notificationFuture,
                firstPendingOrderFuture
        ).join();

        LOGGER.info("[Result CF-1] Order count: {}", orderCountFuture.get());
        LOGGER.info("[Result CF-2] Total revenue: ${}", totalRevenueFuture.get());
        LOGGER.info("[Result CF-3] Customer names: {}", customerNamesFuture.get());
        LOGGER.info("[Result CF-4] Summary: {}", marketSummaryFuture.get());
        Order pending = firstPendingOrderFuture.get();
        if (pending != null) {
            LOGGER.info("[Result CF-6] First pending order customer: {}", pending.getCustomer().getName());
        }
    }
}