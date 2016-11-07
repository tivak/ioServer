package com.top2fox.test.ioServer;

import com.top2fox.test.ioServer.client.ClientNio;
import com.top2fox.test.ioServer.client.FileLoader;
import com.top2fox.test.ioServer.client.LineParser;
import com.top2fox.test.ioServer.client.LinesQueue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        String filePath = "testFile";
        int numThreads = 1;
        int lines = 0;

        if (args.length >= 1) {
            if (Objects.equals(args[0], "-g")) {
                generateFile();
                return;
            }
        }

        if (args.length >= 4) {
            host = args[0];
            port = Integer.parseInt(args[1]);

            filePath = args[2];
            numThreads = Integer.parseInt(args[3]);

            if (args.length >= 5)
                lines = Integer.parseInt(args[4]);
        }

        if (filePath == null || numThreads <= 0) {
            System.out.println("host port filePath numThreads lines");
            return;
        }

        System.out.println("host = " + host);
        System.out.println("port = " + port);
        System.out.println("filePath = " + filePath);
        System.out.println("threads = " + numThreads);
        System.out.println("lines = " + lines);


        LinesQueue<String> linesQueue = new LinesQueue<>();

        Thread loadThread = new Thread(new FileLoader(filePath, lines, linesQueue));
        loadThread.start();

        long start = System.currentTimeMillis();

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(new ClientNio(host, port, new LineParser(linesQueue)));
            thread.start();
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("time - " + (System.currentTimeMillis() - start));
    }

    static void generateFile() {
        int lineNumber = 100_000_000;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("testFile"))) {
            for (int cnt = 0; cnt < lineNumber; cnt++) {
                bufferedWriter.write(cnt + " " + (lineNumber - cnt) + ": " + UUID.randomUUID() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
