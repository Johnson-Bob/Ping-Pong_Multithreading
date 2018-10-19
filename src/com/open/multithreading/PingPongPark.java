package com.open.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongPark {
    private final static String PING = "Ping";
    private final static String PONG = "Pong";

    static {
        Class<?> ensureLoaded = LockSupport.class;
    }

    private AtomicReference<String> last = new AtomicReference<>(PONG);
    private AtomicReference<Thread> parkedThread = new AtomicReference<>();
    private final AtomicInteger counter = new AtomicInteger(1000000);
    private Utils utils;

    public PingPongPark(boolean demoMode) {
        this.utils = new Utils(demoMode);
        new Thread(() -> this.action(PING)).start();
        new Thread(() -> this.action(PONG)).start();
    }

    private void action(String message) {
        while (counter.get() > 0) {
            if (last.get().equals(message)) {
                parkedThread.set(Thread.currentThread());
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println("Thread was interrupted. Do nothing");
                }
            } else {
                utils.print(message);
                last.set(message);
                counter.getAndDecrement();
//                utils.sleep();
                if (parkedThread.get() != null) {
                    LockSupport.unpark(parkedThread.get());
                }
            }
        }
    }

    public static void main(String[] args) {
        PingPongPark pingPongLock = new PingPongPark(true);

    }


}
