package com.ashwin.learning.advancedConcepts;

import java.util.Random;

public class InterruptingThreads {
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Starting.");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for(int i=0; i<1E8; i++){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("We have been interrupted");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
            }
        });
        t.start();
        Thread.sleep(500);
        t.interrupt();
        t.join();

        System.out.println("Finished.");
    }
}
