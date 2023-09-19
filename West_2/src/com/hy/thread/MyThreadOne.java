package com.hy.thread;

import java.util.stream.IntStream;

/**
 * @author CXQ
 * @date 2021/11/12
 */
public class MyThreadOne extends Thread{
    static int[] arr1 = {0,2,4,6,8};
    static int[] arr2 = {1,3,5,7,9};

    public static void main(String[] args) {
        MyThreadOne mt = new MyThreadOne();

        Thread t1 = new Thread(()->{
            synchronized (mt){
                for (int j : arr1) {
                    System.out.println(j);
                    try {
                        mt.notify();
                        mt.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (mt){
                IntStream.range(0, arr2.length).forEach(i -> {
                    System.out.println(arr2[i]);
                    try {
                        mt.notify();
                        mt.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        t1.start();
        t2.start();

    }

}
