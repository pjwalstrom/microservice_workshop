package com.microservices.rentaloffer;

import java.util.Random;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionAdder implements MessageHandler {
    protected static Logger logger = LoggerFactory.getLogger(SolutionAdder.class);
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
        connection.deliveryLoop(new SolutionAdder());
    }

    public void handle(String message) {
        logger.info(String.format(" [s] Received: %s", message));
        NeedPacket needPacket = new Gson().fromJson(message, NeedPacket.class);
        if (needPacket.need.equals(Need.CAR_RENTAL_OFFER) && needPacket.getSolutions().isEmpty()) {
            needPacket.proposeSolution("foo is better than foobar, but both are better than some best");
            logger.info(String.format(" [s] Adding solution: %s", needPacket));
            try {
                Thread.sleep(new Random().nextInt(200));
            } catch (InterruptedException e) {

            }
            needPacket.publish(HOST, BUS_NAME);
        }
    }
}
