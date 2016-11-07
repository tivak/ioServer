package com.top2fox.test.ioServer.server;

import com.top2fox.test.ioServer.common.model.Entity;

import java.util.concurrent.atomic.AtomicLong;

public class MessageWriterToNull implements MessageWriter {
    private AtomicLong count = new AtomicLong();

    @Override
    public void writeMessage(Entity entity) {
        long cnt = count.incrementAndGet();
        if (cnt % 100000 == 0)
            System.out.println(cnt);
    }
}
