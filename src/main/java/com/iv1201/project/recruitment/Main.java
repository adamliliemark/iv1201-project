package com.iv1201.project.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Serves as the main class of the recruitment application where the program is initialized.
 */
@SpringBootApplication
public class Main {

    /**
     * Called via the command line when the program is being executed.
     * @param args is a set of arguments entered via the command line.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
