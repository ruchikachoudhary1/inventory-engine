package dev.ruchika.inventory;

public class NaiveInventory{

    private int available;

public NaiveInventory (int initialStock){
   available=initialStock;
  }

  public synchronized boolean reserve(int qty){
    if(available>=qty){
        Thread.yield();
        available-=qty;
        return true;
    }
    return false;
  }
  public int available(){
    return available;
  }

}