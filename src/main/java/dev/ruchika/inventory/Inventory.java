package dev.ruchika.inventory;

public interface Inventory {

    boolean reserve(int qty);

    int available();
    
}

