package com.ashwin.learning.advancedConcepts;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AvoidDeadlockUsingReentarrantLock {
    public static void main(String[] args) {
        DeadlockRunner dr= new DeadlockRunner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    dr.firstThread();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted.");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    dr.secondThread();
                }catch(InterruptedException ex){
                    System.out.println("Thread Interrupted.");
                }
            }
        });
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException ex){
            System.out.println("Thread Interrupted");
        }
        dr.finalize();
    }
}

class DeadlockRunner{

    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLock(Lock firstLock, Lock secondLock) throws InterruptedException{
        boolean gotFirstLock = false, gotSecondLock = false;
        while(true){
            try{
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            }finally {
                if(gotFirstLock && gotSecondLock) return;
                if(gotFirstLock) firstLock.unlock();
                if(gotSecondLock) secondLock.unlock();
            }
            Thread.sleep(1);
        }
    }
    
    public void firstThread() throws InterruptedException{
        Random random = new Random();

        acquireLock(lock1, lock2);
        try{
            for(int i=0; i<10000; i++){
                Account.transfer(acc1,acc2,random.nextInt(100));
            }
        }finally {
            lock1.unlock();
            lock2.unlock();
        }
    }
    public void secondThread() throws InterruptedException{
        Random random = new Random();

        acquireLock(lock2, lock1);
        try{
            for(int i=0; i<10000; i++){
                Account.transfer(acc2,acc1,random.nextInt(100));
            }
        }finally {
            lock1.unlock();
            lock2.unlock();
        }
    }
    public void finalize(){
        System.out.println("Account 1 balance : " + acc1.getBalance());
        System.out.println("Account 2 balance : " + acc2.getBalance());
        System.out.println("Total balance : " + (acc1.getBalance() + acc2.getBalance()));
    }
}

class Account{
    private int balance = 10000;

    public void deposit(int amount){
        balance = balance + amount;
    }
    public void withdraw(int amount){
        balance = balance - amount;
    }
    public int getBalance(){
        return balance;
    }
    public static void transfer(Account a1, Account a2, int amount){
        a1.withdraw(amount);
        a2.deposit(amount);
    }
}
