package com.microservices.rentaloffer;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionAdder2 implements MessageHandler {
    protected static Logger logger = LoggerFactory.getLogger(SolutionAdder2.class);
    private static String HOST;
    private static String BUS_NAME;

    public static void main(String[] args) {
        /*HOST = args[0];
        BUS_NAME = args[1];*/
        HOST = "192.168.59.103:5672";
        BUS_NAME = "pj";
        HOST = "10.0.0.2:5675";
        BUS_NAME = "daffy";

        Connections connection = new Connections(HOST, BUS_NAME);
        connection.deliveryLoop(new SolutionAdder2());
    }

    public void handle(String message) {
        logger.info(String.format(" [s] Received: %s", message));
        NeedPacket needPacket = new Gson().fromJson(message, NeedPacket.class);
        if (needPacket.need.equals(Need.CAR_RENTAL_OFFER) && needPacket.getSolutions().isEmpty()) {
            needPacket.proposeSolution("foobar is better");
            logger.info(String.format(" [s] Adding solution: %s", needPacket));
            needPacket.publish(HOST, BUS_NAME);
        }
    }
}
