package ch.zhaw.prog2.bridge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Controls the traffic passing the bridge
 */
public class TrafficControllerAlternating {

    private Lock mutex = new ReentrantLock();
    private Condition l2r = mutex.newCondition();
    private Condition r2l = mutex.newCondition();
    private boolean bridgeOccupied = false;

    /* Called when a car wants to enter the bridge form the left side */
    public void enterLeft() {
        mutex.lock();
        try {
            while (bridgeOccupied) {
                l2r.await();
            }
            bridgeOccupied = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.unlock();
        }
    }

    /* Called when a wants to enter the bridge form the right side */
    public synchronized void enterRight() {
        mutex.lock();
        try {
            while (bridgeOccupied) {
                r2l.await();
            }
            bridgeOccupied = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.unlock();
        }
    }

    /* Called when the car leaves the bridge on the left side */
    public void leaveLeft() {
        mutex.lock();
        try {
            bridgeOccupied = false;
            l2r.signal();
        } finally {
            mutex.unlock();
        }
    }

    /* Called when the car leaves the bridge on the right side */
    public void leaveRight() {
        mutex.lock();
        try {
            bridgeOccupied = false;
            r2l.signal();
        } finally {
            mutex.unlock();
        }
    }
}
