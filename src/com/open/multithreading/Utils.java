package com.open.multithreading;

public class Utils {
    private final boolean demoMode;

    public Utils(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public void print(String msg) {
        if (this.demoMode) {
            System.out.println(msg);
        }
    }

    public void sleep() {
        if (!demoMode) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
