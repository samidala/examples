package com.techdisqus;


import java.util.concurrent.Semaphore;

public class EvenOddNumbersUsingSemaphone {

    private final Semaphore even = new Semaphore(0);
    private final Semaphore odd = new Semaphore(1);

    public void printEven(int no) throws InterruptedException {
        even.acquire();
        System.out.println(Thread.currentThread().getName() +" printing "+no);
        odd.release();
    }

    public void printOdd(int no) throws InterruptedException {
        odd.acquire();
        System.out.println(Thread.currentThread().getName() +" printing "+no);
        even.release();
    }

    public static void main(String[] args) {

        EvenOddNumbersUsingSemaphone evenOddNumbersUsingSemaphone = new EvenOddNumbersUsingSemaphone();
        new Thread(() -> {
            for(int i= 2; i<=20;i+=2){
                try {
                    evenOddNumbersUsingSemaphone.printEven(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"even thread").start();

        new Thread(() -> {
            for(int i= 1; i<=20;i+=2){
                try {
                    evenOddNumbersUsingSemaphone.printOdd(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"odd thread").start();
    }


}

