package com.microservices.rentaloffer;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Monitor implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(Monitor.class);

    public static void main(String[] args) {
        /*HOST = args[0];
        BUS_NAME = args[1];*/
        String HOST = "192.168.59.103:5672";
        String BUS_NAME = "pj";
        HOST = "10.0.0.2:5675";
        BUS_NAME = "daffy";

        Connections connection = new Connections(HOST, BUS_NAME);
        connection.deliveryLoop(new Monitor());
    }

    public void handle(String message) {
        logger.info(String.format(" [x] Received: %s", message));
    }

}
