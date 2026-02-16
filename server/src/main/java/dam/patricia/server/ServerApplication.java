package dam.patricia.server;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Servidor REST en Spring Boot.
 * Arranca en el puerto 12345 y se conecta a SQLite.
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) throws Exception {
        // Crea la carpeta "data" si no existe (en el directorio desde el que se ejecute)
        Files.createDirectories(Path.of("data"));

        SpringApplication.run(ServerApplication.class, args);
    }
}
