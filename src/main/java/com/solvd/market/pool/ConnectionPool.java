package com.solvd.market.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 5;

    private static volatile ConnectionPool instance;
    private final BlockingQueue<Connection> pool;

    private ConnectionPool() {
        pool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 1; i <= POOL_SIZE; i++) {
            pool.offer(new Connection(i));
        }
        LOGGER.info("ConnectionPool initialized with {} connections", POOL_SIZE);
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        LOGGER.info("{} is waiting for a connection...", Thread.currentThread().getName());
        Connection connection = pool.take();
        LOGGER.info("{} acquired Connection-{}", Thread.currentThread().getName(), connection.getId());
        return connection;
    }

    public void releaseConnection(Connection connection) {
        pool.offer(connection);
        LOGGER.info("{} released Connection-{}", Thread.currentThread().getName(), connection.getId());
    }
}