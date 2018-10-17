package com.open.multithreading;

public class Task {
    private Object obj = new Object();

    private void waitTask() {
        try {
            obj.wait();
            System.out.println("OK!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void action1() {
        try {
            synchronized (obj) {
                System.out.println("action1");
                obj.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Task().waitTask();
//        new Task().action1();
    }
}
