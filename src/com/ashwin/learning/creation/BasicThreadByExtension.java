package com.ashwin.learning.creation;

public class BasicThreadByExtension {
    public static void main(String[] args){
        ExtendedRunner thread1 = new ExtendedRunner();
        thread1.setName("Thread 1");
        thread1.start();
    }
}

class ExtendedRunner extends Thread{

    @Override
    public void run() {
        System.out.println("Thread"+ getName() +" Says Hello");
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
}
