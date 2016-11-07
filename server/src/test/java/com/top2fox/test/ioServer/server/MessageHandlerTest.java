package com.top2fox.test.ioServer.server;

import com.top2fox.test.ioServer.common.model.Entity;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageHandlerTest extends Assert {
    private final String message = "{\"intParam1\":2,\"intParam2\":0,\"uid\":\"d9e10d4e-7929-4d0b-9ae4-cc6994dc35c5\"}";

    @Test
    public void test() throws InterruptedException {
        List<Entity> entities = new LinkedList<>();

        MessageWriter messageWriter = entities::add;

        BlockingQueue<byte[]> queue = new ArrayBlockingQueue<>(1024);

        MessageHandler messageHandler = new MessageHandler(queue, messageWriter);
        new Thread(messageHandler).start();

        queue.put(new byte[10]);
        Thread.sleep(100);
        assertEquals(entities.size(), 0);

        queue.put(message.getBytes());
        Thread.sleep(100);
        assertEquals(entities.size(), 1);
    }

    @Test
    public void handleTest() throws InterruptedException {
        List<Entity> entities = new LinkedList<>();

        MessageWriter messageWriter = entities::add;

        MessageHandler messageHandler = new MessageHandler(null, messageWriter);
        new Thread(messageHandler).start();

        messageHandler.handleMessage(new byte[10]);
        assertEquals(entities.size(), 0);

        messageHandler.handleMessage(message.getBytes());
        assertEquals(entities.size(), 1);
    }
}
