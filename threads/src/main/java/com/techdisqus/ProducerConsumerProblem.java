package com.techdisqus;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerProblem {

    private Queue<String> queue = new LinkedList<>();
    private final int capacity = 3;
    private final Lock lock = new ReentrantLock(true);
    private final Condition fullCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();

    public void put(String data){
        try {
            lock.lock();
           // System.out.println("current size " +queue.size() + " and capacity "+capacity);
            while (queue.size() == capacity) {

                try {
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(data);
            emptyCondition.signalAll();
            System.out.println(Thread.currentThread().getName()+" added "+ data);
            //System.out.println("queue size "+queue.size());
        }finally {

        }

    }

    public String poll(){
        try {
            lock.lock();
            while (queue.size() == 0) {
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String data = queue.poll();
            fullCondition.signalAll();
            return data;
        }finally {

        }

    }

    public synchronized void putSync(String data){
        try {

            // System.out.println("current size " +queue.size() + " and capacity "+capacity);
            while (queue.size() == capacity) {

                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(data);
            System.out.println(Thread.currentThread().getName()+" added "+ data);
            //System.out.println("queue size "+queue.size());
        }finally {
            notifyAll();
        }

    }

    public synchronized String pollSync(){
        try {

            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String data = queue.poll();
            return data;
        }finally {
            notifyAll();
        }

    }

    public static void main(String[] args) {

        ProducerConsumerProblem producerConsumerProblem = new ProducerConsumerProblem();

        for(int i = 1; i <= 2; i++){
            new Thread(() -> {
                while (true) {
                    String data =producerConsumerProblem.poll();
                    System.out.println(Thread.currentThread().getName()+" received "+data);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c-t-"+i).start();
        }


        for(int i = 1; i <= 3; i++){
            new Thread(() -> {
                int x = 1;
                while (true) {
                    String data = (Thread.currentThread().getName()+ " data "+(x++));
                  //  System.out.println(Thread.currentThread().getName()+" producing "+ data);
                    producerConsumerProblem.put(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            },"p-t-"+i).start();
        }
    }
}
