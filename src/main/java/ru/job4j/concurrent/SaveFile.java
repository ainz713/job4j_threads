package ru.job4j.concurrent;

import java.io.*;

public class SaveFile {

    private final File file;
    private final String content;

    public SaveFile(File file, String content) {
        this.file = file;
        this.content = content;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) {
            w.write(content);
        }
    }
}
