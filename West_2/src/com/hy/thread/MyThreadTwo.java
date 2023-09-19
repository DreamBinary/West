package com.hy.thread;

/**
 * @author CXQ
 * @date 2021/11/12
 */
public class MyThreadTwo implements Runnable{
    int []arr = new int[5];
    static int[] arr1 = {0,2,4,6,8};
    static int[] arr2 = {1,3,5,7,9};
    static MyThreadTwo mt = new MyThreadTwo();

    public MyThreadTwo(){

    }
    public MyThreadTwo(int[] arr) {
        this.arr = arr;
    }
    public static void main(String[] args) {
        MyThreadTwo mt1 =new MyThreadTwo(arr1);
        MyThreadTwo mt2 = new MyThreadTwo(arr2);
        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt2);
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        synchronized (mt){
            for (int i = 0; i < mt.arr.length; i++) {
                System.out.println(arr[i]);
                try {
                    mt.notify();
                    mt.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
