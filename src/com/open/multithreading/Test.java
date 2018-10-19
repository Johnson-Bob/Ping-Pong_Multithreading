package com.open.multithreading;

import java.util.concurrent.locks.LockSupport;

public class Test {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("begin");
        new Thread(() -> {
            LockSupport.unpark(t);
            System.out.println(t.getState());
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.park();
        System.out.println("end");
    }
}
