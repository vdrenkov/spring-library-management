package dev.vdrenkov.slm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLibraryManager {
    private SpringLibraryManager() {
        /* This utility class should not be instantiated */
    }

    static void main(String[] args) {
        SpringApplication.run(SpringLibraryManager.class, args);
    }
}
