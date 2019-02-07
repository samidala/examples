package com.techdisqus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerProblemUsingSemaphore {

    private Queue<String> queue = new LinkedList<>();
    private final int capacity = 3;
    private final Lock lock = new ReentrantLock(true);
    private final Condition fullCondition = lock.newCondition();
    private final Condition emptyCondition = lock.newCondition();

    private final Semaphore producerSemaphore = new Semaphore(1);
    private final Semaphore consumerSemaphore = new Semaphore(0);

    public void put(String data){
        try {

            try {
                producerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("current size " +queue.size() + " and capacity "+capacity);
            queue.offer(data);
            consumerSemaphore.release();

            System.out.println(Thread.currentThread().getName()+" added "+ data);
            //System.out.println("queue size "+queue.size());
        }finally {

        }

    }

    public String poll(){
        try {
            try {
                consumerSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String data = queue.poll();
            producerSemaphore.release();
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

        ProducerConsumerProblemUsingSemaphore producerConsumerProblem = new ProducerConsumerProblemUsingSemaphore();

        for(int i = 1; i <= 2; i++){
            new Thread(() -> {
                while (true) {
                    String data =producerConsumerProblem.poll();
                    System.out.println(Thread.currentThread().getName()+" recvd "+data);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },"c-t-"+i).start();
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);

        for(int i = 1; i <= 3; i++){
            new Thread(() -> {
                int x = 1;
                while (true) {
                    String data =  " data "+atomicInteger.incrementAndGet();
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
