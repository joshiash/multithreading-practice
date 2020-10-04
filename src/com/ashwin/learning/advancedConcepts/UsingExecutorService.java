package com.ashwin.learning.advancedConcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UsingExecutorService {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for(int i=0; i<5; i++){
            executor.submit(new ESProcessor(i));
        }

        executor.shutdown();

        System.out.println("Completed submitting all tasks");

        try{
            executor.awaitTermination(1, TimeUnit.DAYS);
        }catch(InterruptedException ex){
            System.out.println("Thread interrupted.");
        }


        System.out.println("Completed processing all tasks");
    }
}

class ESProcessor implements Runnable{

    private int id;

    public ESProcessor(int id){
        this.id = id;
    }

    public void run(){
        System.out.println("Started processing Thread: " + id);

        try {
            Thread.sleep(5000);
        }catch(InterruptedException ex){
            System.out.println("Thread " + id +" interrupted.");
        }

        System.out.println("Completed processsing Thread: " + id);

    }
}
