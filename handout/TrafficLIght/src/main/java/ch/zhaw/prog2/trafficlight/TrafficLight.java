package ch.zhaw.prog2.trafficlight;

import static java.lang.Thread.sleep;

class TrafficLight {
    private boolean red;

    public TrafficLight() {
        red = true;
    }

    public synchronized void passby() {
        // ToDo: wait as long the light is red
        while (red){
            try {
                wait();
            } catch (InterruptedException e) {
                //dummy
            }
        }
    }

    public synchronized void switchToRed() {
        red = true;
    }

    public synchronized void switchToGreen() {
        red = false;
        notifyAll();
        //notifyAll because otherwise only one car can pass per light
        // waiting cars can now pass by
    }
}
