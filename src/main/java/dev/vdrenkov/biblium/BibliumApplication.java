package dev.vdrenkov.biblium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstraps the Biblium API application.
 * <p>
 * This class provides the main entry point used by Spring Boot to initialize the application context,
 * Auto-configuration and embedded web server.
 * </p>
 */
@SpringBootApplication
public class BibliumApplication {
    /**
     * Prevents instantiation of this bootstrap class.
     */
    private BibliumApplication() {
        /* static bootstrap class */
    }

    /**
     * Starts the Spring Boot application.
     *
     * @param args
     *     Command-line arguments passed at startup.
     */
    static void main(final String[] args) {
        SpringApplication.run(BibliumApplication.class, args);
    }
}
