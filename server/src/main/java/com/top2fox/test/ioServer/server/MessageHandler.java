package com.top2fox.test.ioServer.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.top2fox.test.ioServer.common.model.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;

public class MessageHandler implements Runnable {
    private static Logger logger = LogManager.getLogger(MessageHandler.class);

    private BlockingQueue<byte[]> entityQueue;
    private MessageWriter messageWriter;
    private Gson gson = new Gson();

    public MessageHandler(BlockingQueue<byte[]> queue, MessageWriter messageWriter) {
        this.entityQueue = queue;
        this.messageWriter = messageWriter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] message = entityQueue.take();
                handleMessage(message);
            } catch (InterruptedException ex) {
                logger.error("error in handler", ex);
            }
        }
    }

    void handleMessage(byte [] message) {
        String str = null;
        try {
            str = new String(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (str != null) {
            try {
                Entity entity = gson.fromJson(str, Entity.class);
                messageWriter.writeMessage(entity);
            } catch (JsonSyntaxException ex) {
                logger.error("error parse message: " + str, ex);
            }
        }
    }
}
