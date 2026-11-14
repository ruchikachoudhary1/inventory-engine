package dev.ruchika.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public final class RaceHarness {

    private RaceHarness() {
    }

    public static int reserveAtOnce(Inventory inventory, int shoppers) throws InterruptedException {
        AtomicInteger successes = new AtomicInteger();
        CountDownLatch startGate = new CountDownLatch(1);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < shoppers; i++) {
            Thread shopper = new Thread(() -> {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    return;
                }
                if (inventory.reserve(1)) {
                    successes.incrementAndGet();
                }
            });
            threads.add(shopper);
            shopper.start();
        }

        startGate.countDown();
        for (Thread shopper : threads) {
            shopper.join();
        }
        return successes.get();
    }
}