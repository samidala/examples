package com.techdisqus.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> {

    private final Lock lock = new ReentrantLock();
    private final Condition fullCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();
    private final int capacity;
    private final Queue<T>  queue ;
    private final T killerPill;
    private final CountDownLatch countDownLatch;


    public CustomBlockingQueue(int capacity,T killerPill, final CountDownLatch countDownLatch) {
        this.capacity = capacity;
        this.queue = new LinkedList();
        this.killerPill = killerPill;
        this.countDownLatch = countDownLatch;
    }

    public void put(T t){

        try {
            lock.lock();
            while (queue.size() == capacity) {
                try {
                    System.out.println(Thread.currentThread().getName()+ "waiting on full");
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(t);
            emptyCondition.signalAll();
        }finally {
            lock.unlock();
        }

    }
    public T pool(){
        try{
            lock.lock();
            while (queue.isEmpty()){
                System.out.println(Thread.currentThread().getName()+ " waiting on empty");
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.poll();

            fullCondition.signalAll();
            return t;
        }finally {
            lock.unlock();
        }
    }

    public void put1(T t){

        try {
            while (queue.size() == capacity) {
                try {
                    System.out.println(Thread.currentThread().getName()+ "waiting on full");
                    synchronized (queue){
                        queue.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(t);
            synchronized (queue){
                queue.notifyAll();
            }
        }finally {
           // lock.unlock();
        }

    }
    public T pool1(){
        try{

            while (queue.isEmpty()){
                System.out.println(Thread.currentThread().getName()+ " waiting on empty");
                try {
                    synchronized (queue){
                        queue.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.poll();


            synchronized (queue){
                queue.notifyAll();
            }
            return t;
        }finally {
           // lock.unlock();
        }
    }



}
