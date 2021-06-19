package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        System.out.printf("available processors: %d\n", size);
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        System.out.printf("%s start\n", Thread.currentThread().getName());
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                System.out.printf(
                                        "%s working with\n", Thread.currentThread().getName());
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                System.out.printf(
                                        "%s interrupt\n", Thread.currentThread().getName());
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t
                :threads) {
            t.interrupt();
        }
    }
}
