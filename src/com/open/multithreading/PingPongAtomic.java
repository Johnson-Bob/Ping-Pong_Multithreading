package com.open.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class PingPongAtomic {
    private static String[] MESSAGES = {"Ping", "Pong"};
    private AtomicInteger lastIdx = new AtomicInteger(1);
    private final AtomicInteger counter = new AtomicInteger(1000000);
    private Utils utils;

    public PingPongAtomic(boolean demoMode) {
        this.utils = new Utils(demoMode);
        new Thread(() -> this.action(1, 0)).start();
        new Thread(() -> this.action(0, 1)).start();
    }

    private void action(int msgIdxLast, int msgIdxNew) {
        while (counter.get() > 0) {
            if (lastIdx.compareAndSet(msgIdxLast, msgIdxNew)) {
                utils.print(MESSAGES[msgIdxNew]);
                counter.getAndDecrement();
                utils.sleep();
            }
        }
    }

    public static void main(String[] args) {
        PingPongAtomic pingPongLock = new PingPongAtomic(true);

    }


}
