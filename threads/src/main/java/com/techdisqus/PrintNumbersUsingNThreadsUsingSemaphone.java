package com.techdisqus;

import java.util.concurrent.Semaphore;

public class PrintNumbersUsingNThreadsUsingSemaphone {


    private final int totalThreads;
    private static Semaphore[] semaphores;
    private final int currentThread;

    public PrintNumbersUsingNThreadsUsingSemaphone(int totalThreads, Semaphore[] semaphores, int currentThread) {
        this.totalThreads = totalThreads;
        PrintNumbersUsingNThreadsUsingSemaphone.semaphores = semaphores;
        this.currentThread = currentThread;
        PrintNumbersUsingNThreadsUsingSemaphone.semaphores[0].release();
    }


    public void printNumber(int no) throws InterruptedException {
        semaphores[currentThread].acquire();
       // System.out.println(Thread.currentThread().getName() + " acquired "+currentThread);
        System.out.println(Thread.currentThread().getName()+ " printing "+no);
        if(currentThread  == 2){
           // System.out.println("********************"+currentThread + " releasing 0");
            semaphores[0].release();
        }else {
            //System.out.println("********************"+currentThread + " releasing "+ (currentThread + 1));
            semaphores[currentThread + 1].release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int totalThreads = 3;

        Semaphore[] semaphores = new Semaphore[totalThreads];

        semaphores[0] = new Semaphore(1);
        for(int i =1 ; i < 3;i ++){
            semaphores[i] = new Semaphore(0);
        }


        PrintNumbersUsingNThreadsUsingSemaphone PrintNumbersUsingNThreadsUsingSemaphone0 = new PrintNumbersUsingNThreadsUsingSemaphone(totalThreads,semaphores, 0);
        PrintNumbersUsingNThreadsUsingSemaphone PrintNumbersUsingNThreadsUsingSemaphone1 = new PrintNumbersUsingNThreadsUsingSemaphone(totalThreads,semaphores, 1);
        PrintNumbersUsingNThreadsUsingSemaphone PrintNumbersUsingNThreadsUsingSemaphone2 = new PrintNumbersUsingNThreadsUsingSemaphone(totalThreads,semaphores, 2);
        new Thread(() -> {
            for(int i = 2; i <=10; i+=3){
                try {
                    PrintNumbersUsingNThreadsUsingSemaphone1.printNumber(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread "+1).start();


        new Thread(() -> {
            for(int i = 1; i <=10; i+=3){
                try {
                    PrintNumbersUsingNThreadsUsingSemaphone0.printNumber(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread "+0).start();


        new Thread(() -> {
            for(int i = 3; i <=10; i+=3){
                try {
                    PrintNumbersUsingNThreadsUsingSemaphone2.printNumber(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Thread "+2).start();





    }
}
