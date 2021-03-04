package com.iv1201.project.recruitment.config;

import com.iv1201.project.recruitment.presentation.RestoreController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockBeanConfig {
    public static class EmailDispatcher {
        private static final Logger LOGGER = LoggerFactory.getLogger(RestoreController.class);
        public void sendEmail(String recipient, String content) {
            LOGGER.trace("Email sent to: '" + recipient +"'");
        }
    }
    @Bean
    public EmailDispatcher emailDispatcher() {
        return new EmailDispatcher();
    }

}
