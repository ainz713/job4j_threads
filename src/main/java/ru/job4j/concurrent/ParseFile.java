package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> pred) throws IOException {
        StringBuffer output = new StringBuffer();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if (pred.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public synchronized String getContent() throws IOException {
        return getContent(a -> true);
    }
    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(a -> a < 0x80);
    }
}
