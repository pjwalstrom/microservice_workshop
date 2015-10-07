package com.microservices.rentaloffer;

public class Need {

    private static final String HOTEL_OFFER = "hotel_offer";
    public static final String CAR_RENTAL_OFFER = "car_rental_offer";

    public static void main(String[] args) throws InterruptedException {
        //String host = args[0];
        //String busName = args[1];
        //String host = "192.168.59.103:5672";
        //String busName = "pj";
        String host = "10.0.0.2:5675";
        String busName = "daffy";
        while (true) {
            Thread.sleep(20000);
            new NeedPacket(CAR_RENTAL_OFFER).publish(host, busName);
            //new NeedPacket(CAR_RENTAL_OFFER).publish(host, busName);
            //new NeedPacket(HOTEL_OFFER).publish(host, busName);
            //new NeedPacket("freaking_good_offer").publish(host, busName);
            //Need.publish(host, busName);
        }
    }

    /*public static void publish(String host, String busName) {
        new NeedPacket().publish(host, busName);
        try (Connections connection = new Connections(host, busName)) {
            connection.publish(new NeedPacket().toJson());
            //connection.publish(new FooPacket().toJson());
        } catch (Exception e) {
            throw new RuntimeException("Could not publish message:", e);
        }
    } */
}
