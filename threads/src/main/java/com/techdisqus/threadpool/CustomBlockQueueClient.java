package com.techdisqus.threadpool;

import java.util.concurrent.CountDownLatch;

public class CustomBlockQueueClient {

    public static void main(String[] args) {


        //usingLocks();
        usingSync();



    }
    private static final int killerPill = 20;
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(2);


    private static void usingLocks(){
        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(3,killerPill,COUNT_DOWN_LATCH);

        Runnable producer = () -> {

            for(int i = 1; i <= 20;i++){

                System.out.println("count "+COUNT_DOWN_LATCH.getCount());
                if(COUNT_DOWN_LATCH.getCount() == 0){
                    System.out.println(Thread.currentThread().getName()+ " exiting");
                    return;
                }
                System.out.println(Thread.currentThread().getName()+ " adding " +i);
                queue.put(i);
            }
        };

        Runnable consumer = () -> {
            for(int i = 1; i <= 20;i++){
                int t = queue.pool();
                System.out.println(Thread.currentThread().getName()+ " " +t);
                if(t == killerPill){
                    System.out.println(Thread.currentThread().getName()+ " calling countdown" );
                    COUNT_DOWN_LATCH.countDown();
                    queue.put(t);
                    System.out.println(Thread.currentThread().getName()+ " exiting");
                    return;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        new Thread(consumer,"consumer 1").start();
        new Thread(consumer,"consumer 2 ").start();
        new Thread(producer,"producer 1 ").start();
        new Thread(producer,"producer 2").start();
    }






    private static void usingSync(){

        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(3,killerPill,COUNT_DOWN_LATCH);

        Runnable producer = () -> {

            for(int i = 1; i <= 20;i++){
                System.out.println(Thread.currentThread().getName()+ " adding " +i);
                queue.put1(i);
            }
        };

        Runnable consumer = () -> {
            while (true){
                int val = queue.pool1();
                System.out.println(Thread.currentThread().getName()+ " " +val);
                if(val == killerPill){
                    queue.put(val);
                    System.out.println(Thread.currentThread().getName()+  " exiting!!");
                    //Thread.currentThread().interrupt();
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        new Thread(consumer,"consumer 2 ").start();
          new Thread(consumer,"consumer 1 ").start();
        new Thread(producer,"producer 1 ").start();
        new Thread(producer,"producer 2 ").start();
    }
}
