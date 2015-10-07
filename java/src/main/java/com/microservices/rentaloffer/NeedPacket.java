package com.microservices.rentaloffer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;

public class NeedPacket {

    public String need;
    public UUID uuid;
    public String user;
    public String level;
    private final List<Object> solutions = new ArrayList<>();

    public NeedPacket(String need) {
        this.need = need;
        this.uuid = UUID.randomUUID();
    }

    public String toJson() {
        Map<String, Object> message = new HashMap<>();
        message.put("need", need);
        message.put("solutions", solutions);
        message.put("uuid", uuid.toString());
        int rnd = new Random().nextInt();
        if (rnd%2 == 0)
            message.put("user", "pj");
        message.put("json_class", NeedPacket.class.getName());
        return new Gson().toJson(message);
    }

    public void proposeSolution(Object solution) {
        solutions.add(solution);
    }

    public List<Object> getSolutions() {
        return solutions;
    }

    public void publish(String host, String busName) {
        try (Connections connection = new Connections(host, busName)) {
            connection.publish(this.toJson());
        } catch (Exception e) {
            throw new RuntimeException("Could not publish message:", e);
        }
    }

    @Override
    public String toString() {
        return "NeedPacket{" +
                "NEED='" + need + '\'' +
                ", solutions=" + solutions +
                '}';
    }
}
