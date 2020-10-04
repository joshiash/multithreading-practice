package com.ashwin.learning.advancedConcepts;

import java.util.Random;
import java.util.concurrent.*;

public class InterruptingThreadPool {
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Starting.");

        ExecutorService exec = Executors.newCachedThreadPool();

        Future<?> future = exec.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Random random = new Random();

                for(int i=0; i<1E8; i++){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("We have been interrupted");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
                return null;
            }
        });

        exec.shutdown();
        Thread.sleep(500);
        //Interrupts Thread
        //future.cancel(true);
        exec.shutdownNow();
        exec.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Finished.");
    }
}
