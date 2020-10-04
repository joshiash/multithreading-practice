package com.ashwin.learning.advancedConcepts;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueProducerConsumer {

    public static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        Thread prod = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Producer();
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted");
                }
            }
        });
        Thread cons = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Consumer();
                } catch (InterruptedException e) {
                    System.out.println("Thread Interrupted");
                }
            }
        });

        prod.start();
        cons.start();

        prod.join();
        cons.join();
    }

    public static void Producer() throws InterruptedException{
        Random random = new Random();

        for(int i=0; i<100; i++){
            int value = random.nextInt(100);
            queue.put(value);
            System.out.println("Produced " + value);
        }
    }

    public static void Consumer() throws InterruptedException{
        Thread.sleep(2000);
        while(queue.size() > 0){
            Thread.sleep(100);
            int value = queue.take();
            System.out.println("Consumed " + value);
        }
    }
}
