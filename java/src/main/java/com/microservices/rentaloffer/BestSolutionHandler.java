package com.microservices.rentaloffer;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BestSolutionHandler implements MessageHandler {
    protected static Logger logger = LoggerFactory.getLogger(Monitor.class);
    private static String HOST;
    private static String BUS_NAME;
    private static List<Object> solutions = new ArrayList();

    public static void main(String[] args) {
        /*HOST = args[0];
        BUS_NAME = args[1];*/
        //HOST = "192.168.59.103:5672";
        //BUS_NAME = "pj";
        HOST = "10.0.0.2:5675";
        BUS_NAME = "daffy";

        Connections connection = new Connections(HOST, BUS_NAME);
        connection.deliveryLoop(new BestSolutionHandler());
    }

    public void handle2(String message) {
        logger.info(String.format(" [h] Received: %s", message));
        NeedPacket needPacket = new Gson().fromJson(message, NeedPacket.class);
        if (needPacket.need.equals(Need.CAR_RENTAL_OFFER) && !needPacket.getSolutions().isEmpty()) {
            solutions.addAll(needPacket.getSolutions());
            String bestSolution = findBestSolution(solutions);
            logger.info(String.format(" [h] best solution: %s", bestSolution));
        }
    }

    public void handle(String message) {
        //logger.info(String.format(" [h] Received: %s", message));
        if (message.contains("isBest")) {
            logger.info(String.format(" [h] Received BEST!!: %s", message));
        }

    }

    private String findBestSolution(List<Object> solutions) {
        String bestSolution = "";
        for (Object solution : solutions) {
            String sol = (String)solution;
            if (sol.length() > bestSolution.length()) {
                bestSolution = sol;
            }
        }
        return bestSolution;
    }
}
