package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public int getSize() {
        synchronized (this) {
            return queue.size();
        }
    }

    public void offer(T value) {
        synchronized (this) {
            queue.offer(value);
           this.notifyAll();
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.isEmpty()) {
                try {
                   this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return queue.poll();
        }
    }
}
