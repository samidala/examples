package com.techdisqus;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintNumbersUsingNThreads {

    private static int no = 1;
    private static AtomicInteger threadNo  = new AtomicInteger(1);
    private final int totalThreads;
    private final int currentThread;
    private static final Object lock = new Object();

    public PrintNumbersUsingNThreads(int totalThreads, int currentThread) {
        this.totalThreads = totalThreads;
        this.currentThread = currentThread;
    }

    public void printNumber(){
        int i = 1;
        while (i <= 50) {

            while (currentThread != threadNo.get()) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " printing " + no);
            no++;
            threadNo.incrementAndGet();
            if (threadNo.get() > totalThreads) {
                threadNo.set(1);
            }
            synchronized (lock) {
                lock.notifyAll();
            }
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalThreads = 3;

        new Thread(() -> {
            new PrintNumbersUsingNThreads(totalThreads,2).printNumber();
        },"Thread-"+2).start();

        new Thread(() -> {
            new PrintNumbersUsingNThreads(totalThreads,3).printNumber();
        },"Thread-"+3).start();

        Thread.sleep(2000);
        new Thread(() -> {
            new PrintNumbersUsingNThreads(totalThreads,1).printNumber();
        },"Thread-"+1).start();

    }
}
