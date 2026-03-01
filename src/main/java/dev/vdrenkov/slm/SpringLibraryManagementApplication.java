package dev.vdrenkov.slm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstraps the Spring Library Management API application.
 * <p>
 * This class provides the main entry point used by Spring Boot to initialize the application context,
 * Auto-configuration and embedded web server.
 * </p>
 */
@SpringBootApplication
public class SpringLibraryManagementApplication {

    /**
     * Prevents instantiation of this bootstrap class.
     */
    private SpringLibraryManagementApplication() {
        /* static bootstrap class */
    }

    /**
     * Starts the Spring Boot application.
     *
     * @param args
     *     Command-line arguments passed at startup.
     */
    public static void main(final String[] args) {
        SpringApplication.run(SpringLibraryManagementApplication.class, args);
    }
}
