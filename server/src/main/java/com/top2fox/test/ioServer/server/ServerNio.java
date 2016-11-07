package com.top2fox.test.ioServer.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerNio implements Runnable {
    private static Logger logger = LogManager.getLogger(ServerNio.class);

    private int port = 1234;
    private ServerSocketChannel serverSocket = null;
    private Selector readSelector;
    private BlockingQueue<byte[]> socketQueue = new ArrayBlockingQueue<>(1024 * 1024);

    private int numHandlers;
    private MessageWriter messageWriter;

    public ServerNio(int numHandlers, MessageWriter messageWriter) {
        this.numHandlers = numHandlers;
        this.messageWriter = messageWriter;
    }

    public void start() throws IOException, InterruptedException {
        this.readSelector = Selector.open();

        this.serverSocket = ServerSocketChannel.open();
        this.serverSocket.configureBlocking(false);
        this.serverSocket.bind(new InetSocketAddress(port));

        for (int i = 0; i < numHandlers; i++) {
            new Thread(new MessageHandler(socketQueue, messageWriter)).start();
        }

        long lastTime = System.currentTimeMillis();

        while (true) {
            try{
                // get new sockets
                acceptNewConnections();
                // read new messages
                readMessages();

                if (System.currentTimeMillis() - lastTime > 10*1000) {
                    lastTime = System.currentTimeMillis();
                    logger.info("QueueSize = " + socketQueue.size());
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void acceptNewConnections() throws IOException {
        SocketChannel socketChannel;
        while((socketChannel = this.serverSocket.accept()) != null){
            logger.info("Socket accepted: " + socketChannel);

            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(readSelector, SelectionKey.OP_READ);
            selectionKey.attach(new MessageReader(socketChannel));
        }
    }

    private void readMessages() throws IOException, InterruptedException {
        int readReady = readSelector.selectNow();
        if (readReady > 0) {
            Set<SelectionKey> selectedKeys = readSelector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();

                MessageReader messageReader = (MessageReader) selectionKey.attachment();

                byte[] message;
                while ((message = messageReader.getNext()) != null) {
                    socketQueue.put(message);
                }

                keyIterator.remove();
            }

            selectedKeys.clear();
        }
    }

    @Override
    public void run() {
        try {
            start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}