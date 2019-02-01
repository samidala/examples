package com.techdisqus;

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

    private volatile int counter = 1;

    public void printOdd() throws InterruptedException {
        while(counter <= 10) {
            try {
                // Getting lock for ODD block
                while (counter % 2 == 0){
                    oddCondition.await();
                }
                aLock.lock();
                System.out.println("ODD : "+ counter++);

                // signaling to EVEN condition



            }catch(Exception e){
                System.out.println(e);
            }
                finally {
                if(counter % 2 == 1) {
                    aLock.unlock();
                }
            }
            evenCondition.signal();
        }
    }

    /*
     * EVEN Block
     */
    public void printEven() throws InterruptedException {
        while(counter <= 10) {
            try {
                while (counter % 2 == 1){
                    evenCondition.await();
                }
                // Getting lock for EVEN block
                aLock.lock();
                System.out.println("EVEN : "+ counter++);
                // signaling to ODD condition



            }catch(Exception e){
                System.out.println(e);
            }finally {
                if(counter % 2 == 0) {
                    aLock.unlock();
                }
            }
            oddCondition.signal();
        }
    }

    public static void main(String[] args) {
        EvenOddNumbersUsingLock evenOddNumbersUsingLock = new EvenOddNumbersUsingLock();
        new Thread(() -> {
            try {
                evenOddNumbersUsingLock.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"even thread").start();

        new Thread(() -> {
            try {
                evenOddNumbersUsingLock.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"odd thread").start();
    }
}

