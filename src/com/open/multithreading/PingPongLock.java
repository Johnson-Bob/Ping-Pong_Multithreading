package com.open.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongLock {
    private final static String PING = "Ping";
    private final static String PONG = "Pong";

    private String last = PONG;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final AtomicInteger counter = new AtomicInteger(1000000);
    private Utils utils;

    public PingPongLock(boolean demoMode) {
        this.utils = new Utils(demoMode);
        new Thread(() -> this.action(PING)).start();
        new Thread(() -> this.action(PONG)).start();
    }

    private void action(String message) {
        while (counter.get() > 0) {
            lock.lock();
            try {
                if (last.equals(message)) {
                    await(condition);
                } else {
                    utils.print(message);
                    last = message;
                    counter.getAndDecrement();
                    utils.sleep();
                    condition.signal();
                }
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        PingPongLock pingPongLock = new PingPongLock(true);

    }

    private static void await(Condition condition) {
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
