package com.ashwin.learning.advancedConcepts;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class UsingFuturesAndCallable {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Integer> future = executor.submit(new Callable<Integer>(){

            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                if(duration > 2000){
                    throw new IOException("Sleeping for to long");
                }

                System.out.println("Started Processing");
                try{
                    Thread.sleep(duration);
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted");
                }

                System.out.println("Finished Processing");
                return duration;
            }
        });

        executor.shutdown();

        try {
            System.out.println("Thread slept for " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
}
