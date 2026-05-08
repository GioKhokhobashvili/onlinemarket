package com.solvd.market.threads;

import com.solvd.market.pool.Connection;
import com.solvd.market.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionPoolDemo {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolDemo.class);

    public static void runConnectionPoolDemo() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(7);

        for (int i = 1; i <= 7; i++) {
            final int threadNum = i;
            executor.submit(() -> {
                Connection connection = null;
                try {
                    connection = pool.getConnection();
                    connection.execute("SELECT * FROM orders WHERE thread=" + threadNum);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LOGGER.error("Thread-{} interrupted", threadNum);
                    Thread.currentThread().interrupt();
                } finally {
                    if (connection != null) {
                        pool.releaseConnection(connection);
                    }
                }
            });
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(30, TimeUnit.SECONDS);
        LOGGER.info("All threads finished: {}", finished);
    }
}