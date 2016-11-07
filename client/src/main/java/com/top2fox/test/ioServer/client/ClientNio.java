package com.top2fox.test.ioServer.client;


import com.google.gson.Gson;
import com.top2fox.test.ioServer.common.model.Entity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientNio implements Runnable {
    private String host;
    private int port;
    private LineParser lineParser;

    private SocketChannel socketChannel;
    private Gson gson;
    private ByteBuffer byteBuffer;

    public ClientNio(String host, int port, LineParser lineParser) {
        this.host = host;
        this.port = port;
        this.lineParser = lineParser;
    }

    @Override
    public void run() {
        try {
            byteBuffer = ByteBuffer.allocate(1024);
            gson = new Gson();
            socketChannel = getChannel();
            while (!socketChannel.finishConnect());

            Entity entity;
            while ((entity = lineParser.getNextEntity()) != null) {
                send(entity);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("close thread");
        }
    }

    private SocketChannel getChannel() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(host, port));
        return channel;
    }

    private void send(Entity entity) throws IOException {
        byte[] bytes = gson.toJson(entity).getBytes("UTF-8");

        byteBuffer.clear();
        byteBuffer.put(bytes);
        byteBuffer.put((byte) '\n');
        byteBuffer.flip();

        socketChannel.write(byteBuffer);
    }
}
