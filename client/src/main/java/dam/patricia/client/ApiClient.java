package dam.patricia.client;

import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiClient {
    private final String baseUrl;
    private final HttpClient http;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public String get(String path) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET()
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 400) throw new RuntimeException("HTTP " + res.statusCode() + ": " + res.body());
        return res.body();
    }

    public String post(String path, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 400) throw new RuntimeException("HTTP " + res.statusCode() + ": " + res.body());
        return res.body();
    }

    public String put(String path, String json) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json; charset=utf-8")
                .PUT(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 400) throw new RuntimeException("HTTP " + res.statusCode() + ": " + res.body());
        return res.body();
    }

    public void delete(String path) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .DELETE()
                .build();

        HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (res.statusCode() >= 400) throw new RuntimeException("HTTP " + res.statusCode() + ": " + res.body());
    }
}