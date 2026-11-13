package dev.ruchika.inventory;

public class NaiveInventory implements Inventory{

    private int available;

public NaiveInventory (int initialStock){
   available=initialStock;
  }

  @Override 
  public boolean reserve(int qty){
    if(available>=qty){
        Thread.yield();
        available-=qty;
        return true;
    }
    return false;
  }

  @Override 
  public int available(){
    return available;
  }

}