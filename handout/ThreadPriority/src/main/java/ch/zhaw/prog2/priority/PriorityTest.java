package ch.zhaw.prog2.priority;


public class PriorityTest {
    volatile boolean keepRunning = true;
    public static void main(String[] args) throws InterruptedException {
        int numthreads = 100;
        SimpleThread[] threads = new SimpleThread[numthreads];
        int[] priorities = new int[] { 1, 1, 1 }; // { 1, 5, 10 };
        // start threads
        for (int i = 0; i < numthreads; i++) {
            threads[i] = new SimpleThread(""+i);
            threads[i].setPriority(priorities[i % priorities.length]);
            threads[i].start();
        }
        // wait for some time
        //Thread.sleep(2000);
        // terminate all threads
        for (SimpleThread thread : threads) {
            thread.stopThread();
        }
        // print results
        for (SimpleThread thread : threads) {
            System.out.println(thread.getName() +
                    " : " + thread.getPriority() +
                    " = " + thread.count );
        }
        System.out.println("main exits " + Thread.currentThread().toString());
    }

    private static class SimpleThread extends Thread {
        long count = 0;
        boolean keepRunning = true;

        public SimpleThread(String str) { super(str); }

        public void stopThread(){
            keepRunning = false;
            this.interrupt();
        }

        @Override
        public void run() {
            while (keepRunning) {
                count++;
                Thread.yield();
            }
        }
    }
}
