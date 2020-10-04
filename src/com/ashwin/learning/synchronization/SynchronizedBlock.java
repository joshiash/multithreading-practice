package com.ashwin.learning.synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedBlock {

    private static Random random = new Random();

    private static Object mutexLock1 = new Object();
    private static Object mutexLock2 = new Object();

    private static List<Integer> list1 = new ArrayList<Integer>();
    private static List<Integer> list2 = new ArrayList<>();

    public static void stepOne(){
        synchronized (mutexLock1){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public static void stepTwo(){
        synchronized (mutexLock2){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    public static void process(){
        for(int i=0;i<1000;i++){
            stepOne();
            stepTwo();
        }
    }

    public static void main(String[] args)
    {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                process();
            }
        });

        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                process();
            }
        });

        long start = System.currentTimeMillis();

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Threads completed successfully in " + (end-start));
        System.out.println("List1 count is " + list1.size());
        System.out.println("List2 count is " + list2.size());
    }

}
