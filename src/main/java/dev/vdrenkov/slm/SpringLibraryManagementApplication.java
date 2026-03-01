package dev.vdrenkov.slm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLibraryManagementApplication {
    private SpringLibraryManagementApplication() {
        /* This utility class should not be instantiated */
    }

    public static void main(final String[] args) {
        SpringApplication.run(SpringLibraryManagementApplication.class, args);
    }
}
