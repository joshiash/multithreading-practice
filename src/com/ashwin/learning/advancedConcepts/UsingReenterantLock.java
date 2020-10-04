package com.ashwin.learning.advancedConcepts;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UsingReenterantLock {

    public static void main(String[] args) {
        ReentarantRunner rr= new ReentarantRunner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    rr.firstThread();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted.");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    rr.secondThread();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted.");
                }
            }
        });
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }
        rr.printCount();
    }
}

class ReentarantRunner{

    private int count;
    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private void increment(){
        for(int i =0; i<10000; i++){
            count++;
        }
    }

    public void firstThread() throws InterruptedException{
        lock.lock();
        System.out.println("In First Thread. Going for waiting.");
        cond.await();
        System.out.println("Awake! Processing the remaining values.");
        try{
            increment();
        }finally {
            lock.unlock();
        }
    }
    public void secondThread() throws InterruptedException{
        Thread.sleep(2000);
        lock.lock();
        System.out.println("In second Thread. Waiting for input");
        new Scanner(System.in).nextLine();
        cond.signal();
        System.out.println("Signal Given");
        try{
            increment();
        }finally {
            System.out.println("Second Thread lock release!");
            lock.unlock();
        }
    }

    public void printCount(){
        System.out.println("Count : " + count);
    }
}
