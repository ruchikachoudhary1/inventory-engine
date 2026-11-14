package dev.ruchika.inventory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest{
    private void checkContract(Inventory inventory) {
        assertEquals(5, inventory.available());

        assertFalse(inventory.reserve(6));
        assertEquals(5, inventory.available());

        assertTrue(inventory.reserve(3));
        assertEquals(2, inventory.available());

        assertFalse(inventory.reserve(3));
        assertEquals(2, inventory.available());

        assertTrue(inventory.reserve(2));
        assertEquals(0, inventory.available());
    }

    @Test
    void naiveFollowsContract() {
        checkContract(new NaiveInventory(5));
    }

    @Test
    void synchronizedFollowsContract() {
        checkContract(new SynchronizedInventory(5));
    }

        @Test
    void synchronizedNeverOversells() throws InterruptedException {
        for (int run = 1; run <= 20; run++) {
            Inventory inventory = new SynchronizedInventory(100);
            int sold = RaceHarness.reserveAtOnce(inventory, 200);
            assertEquals(100, sold, "run " + run + ": reserved");
            assertEquals(0, inventory.available(), "run " + run + ": left");
        }
    }
}
