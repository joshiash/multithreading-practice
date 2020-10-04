package com.ashwin.learning.advancedConcepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UsingSemaphore {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i=0; i<200; i++)
        {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getConnection().connect();
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Connection{
    private static Connection instance = new Connection();

    private Semaphore sem = new Semaphore(10);

    private int connections =0;

    private Connection(){
    }

    public static Connection getConnection(){
        return instance;
    }

    public void connect(){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            doconnect();
        }finally {
            sem.release();
        }
    }

    public void doconnect(){

        synchronized (this){
            connections++;
            System.out.println("Connections count :" + connections);
        }

        try{
            Thread.sleep(2000);
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }

        synchronized (this){
            connections--;
        }
    }
}
