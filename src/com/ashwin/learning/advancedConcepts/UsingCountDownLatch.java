package com.ashwin.learning.advancedConcepts;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UsingCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for(int i=0; i<3; i++){
            executor.submit(new LatchProcessor(latch));
        }

        executor.shutdown();

        try{
            latch.await();
            executor.awaitTermination(1, TimeUnit.DAYS);
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }



        System.out.println("Completed");

    }
}

class LatchProcessor implements Runnable{

    private CountDownLatch latch;

    public LatchProcessor(CountDownLatch latch){
        this.latch = latch;
    }

    public void run(){
        System.out.println("Thread started");

        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }

        latch.countDown();

        System.out.println("Thread completed with latch as " + latch);
    }
}
