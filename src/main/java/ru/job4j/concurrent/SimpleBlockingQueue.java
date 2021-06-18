package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public int getSize() {
        synchronized (this) {
            return queue.size();
        }
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= size) {
                wait();
            }
            queue.offer(value);
           this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            this.notifyAll();
            return queue.poll();
        }
    }
}
