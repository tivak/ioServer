package com.top2fox.test.ioServer.client;

import com.top2fox.test.ioServer.common.model.Entity;

public class LineParser {
    private final LinesQueue<String> linesQueue;

    public LineParser(LinesQueue<String> linesQueue) {
        this.linesQueue = linesQueue;
    }

    public Entity getNextEntity() throws InterruptedException {
        linesQueue.clear();
        String line;
        while ((line = linesQueue.poll()) == null && !linesQueue.isClosed()) {
//            wait
        }

        if (line != null) {
            String[] split = line.split(" ");
            if (split.length == 3) {
                return new Entity()
                        .setIntParam1(Integer.parseInt(split[0]))
                        .setIntParam2(Integer.parseInt(split[1].substring(0, split[1].length() - 1)))
                        .setUid(split[2]);
            }
        }

        return null;
    }
}
