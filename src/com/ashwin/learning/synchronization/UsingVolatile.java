package com.ashwin.learning.synchronization;

import java.util.Scanner;

public class UsingVolatile {
    public static void main(String[] args){
        Processor processor = new Processor();
        processor.start();

        System.out.println("Press any key to stop thread.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Shutdown initiated.");

        processor.shutdown();
    }
}

class Processor extends Thread{
    /*
    * Volatile keyword tells the program to always get the variable value from memory as apposed to local cache.
    * */
    private volatile boolean running = true;

    @Override
    public void run() {
        while(running){
            System.out.println("Thread is running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown(){
        running = false;
    }
}
