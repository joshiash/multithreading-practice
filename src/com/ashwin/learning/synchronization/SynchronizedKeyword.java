package com.ashwin.learning.synchronization;

public class SynchronizedKeyword {

    private static int count;

    private static synchronized void increment(){
        count++;
    }

    public static void main(String[] args){
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i<10000; i++){
                    increment();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i<10000; i++){
                    increment();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Count is " + count);
    }
}
