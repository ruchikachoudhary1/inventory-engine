 package dev.ruchika.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceDemo {
    public static void main(String[]args)throws InterruptedException{
            int stock=100;
            int shoppers=200;

            NaiveInventory inventory=new NaiveInventory(stock);
            AtomicInteger successes= new AtomicInteger();

            CountDownLatch startGate=new CountDownLatch(1);
            
            List<Thread>threads=new ArrayList<>();

            for(int i=0;i<shoppers;i++){
                Thread shopper=new Thread(() ->{
                    try{
                        startGate.await();
                    }catch(InterruptedException e){
                        return;
                    }
                    if(inventory.reserve(1)){
                        successes.incrementAndGet();
                    }
                });
                threads.add(shopper);
                shopper.start(); 
            }
            

            startGate.countDown();

            for(Thread shopper : threads){
                shopper.join();
            }

            int sold=successes.get();
            int left=inventory.available();

            System.out.println("Stock at start: "+stock);
            System.out.println("Reserved:       "+sold);
            System.out.println("Left:           "+left);
            System.out.println(sold<=stock && sold+left==stock?"CORRECT" : "BROKEN: oversold or lost an update");
    }

}
