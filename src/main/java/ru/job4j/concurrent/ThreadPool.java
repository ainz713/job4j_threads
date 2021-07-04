package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        System.out.printf("available processors: %d%s", size, System.lineSeparator());
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        System.out.printf("%s start%s", Thread.currentThread().getName(), System.lineSeparator());
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                System.out.printf(
                                        "%s working with%s", Thread.currentThread().getName(), System.lineSeparator());
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                System.out.printf(
                                        "%s interrupt%s", Thread.currentThread().getName(), System.lineSeparator());
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread t
                :threads) {
            t.interrupt();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("job1"));
        threadPool.work(() -> System.out.println("job2"));
        threadPool.work(() -> System.out.println("job3"));
        threadPool.work(() -> System.out.println("job4"));
        threadPool.work(() -> System.out.println("job5"));
        threadPool.shutdown();
    }
}
