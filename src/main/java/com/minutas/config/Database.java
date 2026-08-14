package com.minutas.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:db/minutas.db";
    private static Connection connection;

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Ensure db directory exists
                java.io.File dbDir = new java.io.File("db");
                if (!dbDir.exists()) {
                    dbDir.mkdirs();
                }
                connection = DriverManager.getConnection(DB_URL);
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos SQLite", e);
        }
        return connection;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
             
            // Check if tables already exist
            var rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='conjunto'");
            boolean exists = rs.next();
            
            if (!exists) {
                executeSqlScript(conn, "/db/schema.sql");
                executeSqlScript(conn, "/db/seed.sql");
                System.out.println("Base de datos inicializada con schema y seed exitosamente.");
            }
        } catch (Exception e) {
            // Fallback to reading file directly if resource stream fails
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                var rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='conjunto'");
                if (!rs.next()) {
                    executeSqlFile(conn, "db/schema.sql");
                    executeSqlFile(conn, "db/seed.sql");
                    System.out.println("Base de datos inicializada desde archivos locales.");
                }
            } catch (Exception ex) {
                throw new RuntimeException("Error crítico inicializando la base de datos", ex);
            }
        }
    }

    private static void executeSqlScript(Connection conn, String resourcePath) {
        try (InputStream is = Database.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String sql = reader.lines().collect(Collectors.joining("\n"));
            executeMultiStatement(conn, sql);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer el script SQL: " + resourcePath, e);
        }
    }

    private static void executeSqlFile(Connection conn, String filePath) {
        try {
            String sql = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)), StandardCharsets.UTF_8);
            executeMultiStatement(conn, sql);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer el archivo SQL: " + filePath, e);
        }
    }

    private static void executeMultiStatement(Connection conn, String sql) throws Exception {
        try (Statement stmt = conn.createStatement()) {
            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement);
                }
            }
        }
    }
}
