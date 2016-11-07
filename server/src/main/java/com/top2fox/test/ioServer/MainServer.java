package com.top2fox.test.ioServer;

import com.top2fox.test.ioServer.server.*;

import java.io.*;
import java.util.Objects;

public class MainServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        MessageWriter messageWriter = new MessageWriterToNull();
        int threads = 1;

        if (args.length >= 2) {
            if (Objects.equals(args[0], "-f"))
                messageWriter = new MessageWriterToLog();
            else if (Objects.equals(args[0], "-n"))
                messageWriter = new MessageWriterToNull();

            threads = Integer.parseInt(args[1]);
        }

        if (messageWriter == null || threads <= 0) {
            System.out.println("-n|-f numThreads");
            System.out.println("-f write to file");
            System.out.println("-n write to null");
            return;
        }

        new ServerNio(threads, messageWriter).start();
    }
}
