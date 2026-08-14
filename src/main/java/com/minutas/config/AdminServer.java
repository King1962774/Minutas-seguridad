package com.minutas.config;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minutas.model.*;
import com.minutas.service.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AdminServer {
    private static HttpServer server;
    private static final String API_KEY = "secret-token-123";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void startServer(int port) {
        if (server != null) return;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            
            server.createContext("/api/login", exchange -> handlePost(exchange, (body, headers) -> {
                String username = (String) body.get("username");
                String password = (String) body.get("password");
                AutenticacionService auth = new AutenticacionService();
                var userOpt = auth.login(username, password);
                if (userOpt.isPresent()) {
                    return mapper.writeValueAsString(userOpt.get());
                }
                throw new RuntimeException("Credenciales inválidas");
            }));

            server.createContext("/api/visitante", exchange -> handlePost(exchange, (body, headers) -> {
                Visitante v = mapper.convertValue(body.get("visitante"), Visitante.class);
                RegistroVisita rv = mapper.convertValue(body.get("visita"), RegistroVisita.class);
                VisitanteService vs = new VisitanteService();
                vs.registrarVisita(v, rv);
                return "{\"status\":\"success\"}";
            }));

            server.createContext("/api/novedad", exchange -> handlePost(exchange, (body, headers) -> {
                int idConjunto = (int) body.get("id_conjunto");
                int idTurno = (int) body.get("id_turno");
                String cat = (String) body.get("categoria");
                String desc = (String) body.get("descripcion");
                NovedadService ns = new NovedadService();
                ns.agregarNovedad(idConjunto, idTurno, cat, desc);
                return "{\"status\":\"success\"}";
            }));

            server.setExecutor(null);
            server.start();
            System.out.println("Servidor Admin LAN iniciado en puerto " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    private interface RequestHandler {
        String handle(Map<String, Object> body, com.sun.net.httpserver.Headers headers) throws Exception;
    }

    private static void handlePost(HttpExchange exchange, RequestHandler handler) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }
        
        String apiKey = exchange.getRequestHeaders().getFirst("X-API-Key");
        if (apiKey == null || !apiKey.equals(API_KEY)) {
            sendResponse(exchange, 401, "{\"error\":\"Unauthorized API Key\"}");
            return;
        }

        try {
            String bodyStr = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            Map<String, Object> body = mapper.readValue(bodyStr, Map.class);
            String response = handler.handle(body, exchange.getRequestHeaders());
            sendResponse(exchange, 200, response);
        } catch (Exception e) {
            String err = "{\"error\":\"" + e.getMessage() + "\"}";
            sendResponse(exchange, 400, err);
        }
    }

    private static void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
