package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer v;
        do {
            v = count.get();
        } while (!count.compareAndSet(v, v + 1));
    }

    public int get() {
        return count.get();
    }
}
