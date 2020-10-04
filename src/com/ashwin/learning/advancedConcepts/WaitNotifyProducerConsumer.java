package com.ashwin.learning.advancedConcepts;

import java.util.LinkedList;

public class WaitNotifyProducerConsumer {

    public static void main(String[] args) {
        WNProducerConsumer prodCons = new WNProducerConsumer();
        Thread prod = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    prodCons.producer();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted");
                }
            }
        });
        Thread cons = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    prodCons.consumer();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted");
                }
            }
        });

        prod.start();
        cons.start();

        try {
            prod.join();
            cons.join();
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }
    }

}

class WNProducerConsumer{

    private LinkedList<Integer> queue = new LinkedList();
    private final int limit = 10;
    private Object lock = new Object();

    public void producer() throws InterruptedException{

        int value = 0;

        while(true){

            synchronized (lock) {
                while (queue.size() == limit) {
                    lock.wait();
                }
                System.out.println("Produced " + value);
                queue.add(value++);
                lock.notify();
            }
        }
    }

    public void consumer() throws InterruptedException{
        while(true){
            synchronized (lock){
                while(queue.size() == 0){
                    lock.wait();
                }
                System.out.println("list size is " + queue.size());
                int value = queue.removeFirst();
                System.out.println("Consumed " + value);
                lock.notify();
            }
            Thread.sleep(1000);
        }
    }
}
