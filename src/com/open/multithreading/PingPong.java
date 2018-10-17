package com.open.multithreading;

public class PingPong {
    private final static String PING = "Ping";
    private final static String PONG = "Pong";

    private String last = PONG;

    private synchronized void action(String message) {
        while (true) {
            if (last.equals(message)) {
                wait(this);
            } else {
                System.out.println(message);
                last = message;
                sleep(1000);
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        new Thread(() -> pingPong.action(PING)).start();
        new Thread(() -> pingPong.action(PONG)).start();
    }

    private static void wait(Object o) {
        try {
            o.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
