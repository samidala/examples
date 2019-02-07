package com.techdisqus.threadpool;

public class Task implements Runnable{


    @Override
    public void run() {

        System.out.println("current thread "+Thread.currentThread().getName());

    }
}
