package com.iv1201.project.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Serves as the main class of the recruitment application where the program is initialized.
 */
@SpringBootApplication
public class RecruitmentApplication {

    /**
     * Called via the command line when the program is being executed.
     * @param args is a set of arguments entered via the command line.
     */
    public static void main(String[] args) {
        SpringApplication.run(RecruitmentApplication.class, args);
    }
}
