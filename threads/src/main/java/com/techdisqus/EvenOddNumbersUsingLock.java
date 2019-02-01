package com.techdisqus;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EvenOddNumbersUsingLock {

    //Reentrant Lock
    private final Lock aLock = new ReentrantLock();

    // Condition for ODD block
    private final Condition oddCondition = aLock.newCondition();

    // Condition for EVEN block
    private final Condition evenCondition = aLock.newCondition();

    private final AtomicBoolean isTrue = new AtomicBoolean(false);

    private volatile int counter = 1;

    public void printOdd(int no) throws InterruptedException {

        try{
            aLock.lock();
            while (isTrue.get())
            evenCondition.await();
            System.out.println(Thread.currentThread().getName() +" printing "+no);
            isTrue.set(true);
            oddCondition.signal();


        }finally {
            aLock.unlock();
        }
    }

    /*
     * EVEN Block
     */
    public void printEven(int no) throws InterruptedException {
        try{
            aLock.lock();
            while (!isTrue.get())
                oddCondition.await();
            System.out.println(Thread.currentThread().getName() +" printing "+no);
            isTrue.set(false);
            evenCondition.signal();

        }finally {
            aLock.unlock();
        }

    }

    public static void main(String[] args) {
        EvenOddNumbersUsingLock evenOddNumbersUsingLock = new EvenOddNumbersUsingLock();
        new Thread(() -> {
            try {
                for(int i = 2; i <= 10;i+=2){
                evenOddNumbersUsingLock.printEven(i);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"even thread").start();

        new Thread(() -> {
            try {for(int i = 1; i <= 10;i+=2){
                evenOddNumbersUsingLock.printOdd(i);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"odd thread").start();
    }
}

