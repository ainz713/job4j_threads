package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        /* Скачать файл*/
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(getFileNameFromURL(url))) {
                byte[] dataBuffer = new byte[speed];
                int bytesRead;
                long startTime = System.currentTimeMillis();
                long leftTime;
                while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    leftTime = System.currentTimeMillis() - startTime;
                    if (leftTime < 1000) {
                        Thread.sleep(speed - leftTime);
                    }
                    startTime = System.currentTimeMillis();
                }
            } catch (IOException | InterruptedException | URISyntaxException e) {
                Thread.currentThread().interrupt();
            }
    }

    public static String getFileNameFromURL(String url) throws URISyntaxException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_");
        Date date = new Date();
        return dateFormat.format(date) + Paths.get(new URI(url).getPath()).getFileName().toString();
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough args");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
