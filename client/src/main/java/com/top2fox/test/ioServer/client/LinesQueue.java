package com.top2fox.test.ioServer.client;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LinesQueue<T> extends ConcurrentLinkedQueue<T> {
    private boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
