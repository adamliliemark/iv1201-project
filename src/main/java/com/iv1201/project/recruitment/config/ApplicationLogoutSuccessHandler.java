package com.iv1201.project.recruitment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Initiates the proper procedures following a successful application logout.
 */
public class ApplicationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    Logger LOGGER = LoggerFactory.getLogger(ApplicationLogoutSuccessHandler.class);

    /**
     * Called after a successful logout.
     * @param httpServletRequest contains the information regarding the logout HTTP request from the user.
     * @param httpServletResponse contains the information regarding the logout HTTP response.
     * @param authentication contains authentication information of the logged out user.
     * @throws IOException if there is some issues with the user page redirection.
     * @throws ServletException when the servlet encounters difficulty
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        LOGGER.trace(authentication.getName() + " has logged out");
    }
}
