package com.hy.thread;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author CXQ
 * @date 2021/11/12
 */
public class MyThreadThree implements Runnable{
    ArrayList arr = new ArrayList();
    static MyThreadThree mt = new MyThreadThree();
    public MyThreadThree(){

    }
    public MyThreadThree(ArrayList arr) {
        this.arr = arr;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList arr1 = new ArrayList();
        ArrayList arr2 = new ArrayList();
        String str1 = input.nextLine();
        String str2 = input.nextLine();
        String[] str3 = str1.split(" ");
        String[] str4 = str2.split(" ");
        for (String s : str3) {
            arr1.add(s);
        }
        for (String s : str4) {
            arr2.add(s);
        }
        MyThreadThree mt1 =new MyThreadThree(arr1);
        MyThreadThree mt2 = new MyThreadThree(arr2);
        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt2);
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        synchronized (mt){
            IntStream.range(0, arr.size()).forEach(i -> {
                System.out.println(arr.get(i));
                try {
                    mt.notify();
                    mt.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
