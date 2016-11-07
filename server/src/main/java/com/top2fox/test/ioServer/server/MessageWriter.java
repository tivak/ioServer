package com.top2fox.test.ioServer.server;

import com.top2fox.test.ioServer.common.model.Entity;

public interface MessageWriter {
    void writeMessage(Entity entity);
}
