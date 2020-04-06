package ch.zhaw.prog2.circularbuffer;

public class ProtectedCircularBuffer<T> implements Buffer<T> {

    CircularBuffer<T> buff;
    public ProtectedCircularBuffer(Class<T> clazz, int bufferSize){
       this.buff = new CircularBuffer(clazz, bufferSize);
    }

    @Override
    public synchronized boolean put(T element) throws InterruptedException {
        while (full()){
            wait();
        }
        notifyAll();
        return buff.put(element);
    }

    @Override
    public synchronized T get() throws InterruptedException {
        while (empty()){
            wait();
        }
        notifyAll();
        return buff.get();
    }
    /*
        NotifyAll() wird verwendet, da es unter Umständen sein kann, dass get Auftraege und put Auftraege im
        Warteraum sind. Wenn dann ein notify() kommt, kann es sein, dass ein Auftrag geweckt wird der noch nicht
        weitermachen darf, da die Bedingung immernoch so ist, dass er warten muss.
        Dies ist bei kleinem Buffer mit grosser Anzahl Producer & Consumer gut möglich.
     */

    @Override
    public synchronized boolean empty() {
        return buff.empty();
    }

    @Override
    public synchronized boolean full() {
        return buff.full();
    }

    @Override
    public synchronized int count() {
        return buff.count();
    }

    @Override
    public synchronized void printBufferSlots() {
        buff.printBufferSlots();
    }

    @Override
    public synchronized void printBufferContent() {
        buff.printBufferContent();
    }
}
