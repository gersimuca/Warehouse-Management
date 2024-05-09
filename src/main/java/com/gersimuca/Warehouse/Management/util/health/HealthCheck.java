package com.gersimuca.Warehouse.Management.util.health;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HealthCheck {
    public static void main(String[] args) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(args[0]))
                .header("accept", "application/json")
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200 || !response.body().contains("UP")) throw new RuntimeException("Healthcare failed - service not UP: " + args[0]);
        } catch (Exception e) {
            throw new RuntimeException("Healthcheck failed: "+ args[0] + " ;", e);
        }
    }
}

