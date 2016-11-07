package com.top2fox.test.ioServer.server;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;

public class MessageReader {
    private ByteBuffer messageBuffer = ByteBuffer.allocate(1024);
    private SocketChannel socketChannel;
    private ByteBuffer readBuffer;
    private Queue<byte[]> messages = new LinkedList<>();

    public MessageReader(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.messageBuffer = ByteBuffer.allocate(1024);
        this.readBuffer = ByteBuffer.allocate(1024);
    }

    public byte[] getNext() throws IOException {
        byte[] message = messages.poll();
        if (message != null)
            return message;

        readBuffer.clear();

        int read;
        while ((read = socketChannel.read(readBuffer)) > 0) {
            readBuffer(readBuffer);
        }

        if (read == -1)
            socketChannel.close();

        return messages.poll();
    }

    private void readBuffer(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        while(byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            if (b != '\n' && b != '\r') {
                messageBuffer.put(b);
            }
            else if (messageBuffer.position() > 0) {
                messageBuffer.flip();
                byte[] array = new byte[messageBuffer.remaining()];
                messageBuffer.get(array);
                messageBuffer.clear();

                messages.offer(array);
            }
        }
    }
}
