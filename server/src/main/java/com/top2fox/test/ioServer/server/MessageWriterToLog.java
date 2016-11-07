package com.top2fox.test.ioServer.server;

import com.top2fox.test.ioServer.common.model.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MessageWriterToLog implements MessageWriter {
    private static Logger logger = LogManager.getLogger("output");

    @Override
    public void writeMessage(Entity entity) {
        logger.info(entity.getIntParam1() + " " + entity.getIntParam2() + ": " + entity.getUid());
    }
}
