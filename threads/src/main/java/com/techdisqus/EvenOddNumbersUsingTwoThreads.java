package com.techdisqus;

import java.util.concurrent.atomic.AtomicBoolean;

public class EvenOddNumbersUsingTwoThreads {

    private int no  = 1;
    private final AtomicBoolean isEven;

    public EvenOddNumbersUsingTwoThreads() {
        this.isEven = new AtomicBoolean(false);
    }

    public void printEven(){

        while (true) {
            while (!isEven.get()) {
                synchronized (isEven) {
                    try {
                        isEven.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " printing " + no);
            no++;
            if(no > 100){
                System.exit(0);
            }
            isEven.set(false);
            synchronized (isEven) {
                isEven.notifyAll();
            }
        }

    }

    public void printOdd(){

        while (true) {
            while (isEven.get()) {
                synchronized (isEven) {
                    try {
                        isEven.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " printing " + no);
            no++;
            if(no > 100){
                System.exit(0);
            }
            isEven.set(true);
            synchronized (isEven) {
                isEven.notifyAll();
            }
        }

    }

    public static void main(String[] args) {

        EvenOddNumbersUsingTwoThreads evenOddNumbersUsingTwoThreads = new EvenOddNumbersUsingTwoThreads();

        new Thread(() -> {
            evenOddNumbersUsingTwoThreads.printEven();
        },"Even Thread").start();

        new Thread(() -> {
            evenOddNumbersUsingTwoThreads.printOdd();
        },"Odd Thread").start();


    }



}

