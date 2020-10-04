package com.ashwin.learning.creation;

public class BasicThreadByImplementationAnnonomousClass {
    public static void main(String[] args){
        Thread thread1 = new Thread(new Runnable(){

            @Override
            public void run() {
                System.out.println("Thread Says Hello");
                for(int i=0; i<10; i++)
                {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
            }
        });
        thread1.start();
    }
}
