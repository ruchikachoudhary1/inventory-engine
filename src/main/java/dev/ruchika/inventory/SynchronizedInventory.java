package dev.ruchika.inventory;

    public class SynchronizedInventory implements Inventory{
                        public int available;

        public SynchronizedInventory(int initialStock){
            available=initialStock;
        }

        @Override

        public synchronized boolean reserve(int qty){
            if(available>=qty){
                Thread.yield();
                available -=qty;
                return true;
            } 
            return false;
        }

        @Override 

        public synchronized int available(){
            return available;
        }
    }
