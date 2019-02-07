package com.techdisqus.threadpool;

public class TestClient {

    public static void main(String[] args) {

        CustomThreadPool customThreadPool = new CustomThreadPool(3);
        customThreadPool.execute(new Task());
        customThreadPool.execute(new Task());
        customThreadPool.execute(new Task());
    }
}
