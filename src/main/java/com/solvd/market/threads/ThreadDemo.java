package com.solvd.market.threads;

import com.solvd.market.market.Market;
import com.solvd.market.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ThreadDemo {

    private static final Logger LOGGER = LogManager.getLogger(ThreadDemo.class);

    public static void runThreadExamples(Market market) {
        Runnable marketPrinter = () -> {
            LOGGER.info("[Runnable Thread] Market name: {}", market.getName());
            Set<Order> orders = market.getOrders();
            LOGGER.info("[Runnable Thread] Total orders: {}", orders.size());
        };

        Thread runnableThread = new Thread(marketPrinter, "MarketPrinterThread");

        Thread extendedThread = new Thread("OrderSummaryThread") {
            @Override
            public void run() {
                LOGGER.info("[Extended Thread] Summarizing orders for market: {}", market.getName());
                market.getOrders().forEach(order ->
                        LOGGER.info("[Extended Thread] Order status: {} | Customer: {}",
                                order.getOrderStatus(), order.getCustomer().getName()));
            }
        };

        runnableThread.start();
        extendedThread.start();

        try {
            runnableThread.join();
            extendedThread.join();
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}