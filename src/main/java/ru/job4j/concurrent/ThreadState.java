package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        System.out.printf("%s state: %s, %s state: %s\n",
                first.getName(), first.getState(),
                second.getName(), second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s state: %s, %s state: %s\n",
                    first.getName(), first.getState(),
                    second.getName(), second.getState());
        }
        System.out.printf("%s state: %s, %s state: %s\n",
                first.getName(), first.getState(),
                second.getName(), second.getState());
        System.out.println("Работа завершена");
        System.out.println(Thread.currentThread().getName());
    }
}
