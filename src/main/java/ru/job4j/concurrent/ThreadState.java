package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        state(first);
        state(second);
        if (first.getState() == Thread.State.TERMINATED
                && second.getState() == Thread.State.TERMINATED) {
            System.out.println("Работа завершена");
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void state(Thread thread) {
        System.out.println(thread.getName());
        System.out.println(thread.getState());
        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            System.out.println(thread.getState());
        }
        System.out.println(thread.getState());
    }
}
