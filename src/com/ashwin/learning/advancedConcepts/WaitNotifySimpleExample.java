package com.ashwin.learning.advancedConcepts;

import java.util.Scanner;

public class WaitNotifySimpleExample {

    public static void main(String[] args) {
        WNProduce wnProduce = new WNProduce();
        Thread produce = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    wnProduce.produce();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted");
                }
            }
        });
        Thread consume = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    wnProduce.consume();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted");
                }
            }
        });

        produce.start();
        consume.start();

        try {
            produce.join();
            consume.join();
        }catch (InterruptedException ex){
            System.out.println("Thread Interrupted");
        }
    }


}

class WNProduce{
    public void produce() throws InterruptedException{
        synchronized (this){
            System.out.println("Produce started");
            wait();
            System.out.println("Produce resumed");
        }
    }

    public void consume() throws InterruptedException{

        Scanner sc = new Scanner(System.in);

        synchronized (this){
            System.out.println("Consume waiting for return key");
            sc.nextLine();
            System.out.println("Consumer resumed");
            notify();
            Thread.sleep(5000);
            System.out.println("Passing control back");
        }
    }
}
