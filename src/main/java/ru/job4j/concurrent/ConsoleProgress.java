package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
       char[] process = {'\\', '|', '/', '-'};
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.printf("\rload: %c", process[(count++) % 4]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
