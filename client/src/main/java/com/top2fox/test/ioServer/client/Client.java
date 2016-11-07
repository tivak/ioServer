package com.top2fox.test.ioServer.client;

import com.google.gson.Gson;
import com.top2fox.test.ioServer.common.model.Entity;

import java.io.*;
import java.net.Socket;

public class Client {
//    public void run() {
//        try {
//            Socket socket = new Socket("localhost", 1234);
//            PrintStream pstream = new PrintStream
//                    (socket.getOutputStream());
//            for (int i = 100; i >= 0; i--) {
//                pstream.println(i +
//                        " bottles of beer on the wall");
//            }
//            pstream.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void run() {
//        try {
//            Socket socket = new Socket("localhost", 1234);
//            PrintStream pstream = new PrintStream
//                    (socket.getOutputStream());
//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//            String line;
//            while (!Objects.equals(line = bufferedReader.readLine(), "exit")) {
//                if (line.length() == 0) {
//                    System.out.println("Client: println");
//                    pstream.println();
//                }
//                else {
//                    System.out.println("Client: print - " + line);
//                    pstream.print(line);
//                }
//            }
//            pstream.close();
//            socket.close();
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void run() {
//        try {
//            Socket socket = new Socket("localhost", 1234);
//            PrintStream pstream = new PrintStream(socket.getOutputStream());
//
//            LineParser loader = new LineParser("testFileS.txt");
//            loader.load();
//            Gson gson = new Gson();
//            for (Entity entity : loader.getEntities()) {
//                String json = gson.toJson(entity);
//                pstream.println(json);
//                System.out.println(json);
//            }
//
//            pstream.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
