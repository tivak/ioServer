package com.top2fox.test.ioServer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader implements Runnable {
    private String fileName;
    private int readLines;
    private LinesQueue<String> linesQueue;

    public FileLoader(String fileName, int readLines, LinesQueue<String> linesQueue) {
        this.fileName = fileName;
        this.readLines = readLines;
        this.linesQueue = linesQueue;
    }

    @Override
    public void run() {
        long count = 0;
        long lastTime = System.currentTimeMillis();

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (readLines > 0 && count > readLines)
                    break;

                if (count % 1_000_000 == 0)
                    System.out.println("readied - " + count);

                count++;
                linesQueue.add(line);

                if (System.currentTimeMillis() - lastTime > 10_000) {
                    lastTime = System.currentTimeMillis();
                    System.out.println("QueueSize = " + linesQueue.size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            linesQueue.setClosed(true);
        }
    }
}
