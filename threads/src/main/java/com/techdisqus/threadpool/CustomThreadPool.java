package com.techdisqus.threadpool;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {

    private final Queue<Runnable> queue;
    private final Thread[] poolThreads;

    private final int capacity;
    public CustomThreadPool(int capacity){
        this.capacity = capacity;
        queue = new LinkedBlockingQueue<>(capacity);
        poolThreads = new Thread[capacity];
        for(int i = 0 ; i <capacity; i++){
            poolThreads[i] = new Thread(new PoolThread());
            poolThreads[i].start();
        }
    }

    public void execute(Runnable runnable){
        synchronized (queue) {
            queue.offer(runnable);
            queue.notifyAll();
        }
    }
    private   class PoolThread implements Runnable{

        @Override
        public void run() {
            while (true) {
                while (queue.isEmpty()) {
                    synchronized (queue) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Runnable runnable = queue.poll();
                try {
                    runnable.run();
                } catch (Exception e) {

                }
            }

        }
    }
}
