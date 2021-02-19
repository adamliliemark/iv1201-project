package com.iv1201.project.recruitment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Initiates the proper procedures following a successful login.
 */
public class ApplicationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Called after a successful login.
     * @param httpServletRequest contains the information regarding the login HTTP request from the user.
     * @param httpServletResponse contains the information regarding the HTTP response from the server to the request from the user.
     * @param authentication contains authentication information of the logged in user.
     * @throws IOException is thrown if there is some issues with the user page redirection.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        LOGGER.trace(authentication.getName() + " has logged in");

        if (httpServletResponse.isCommitted()) {
            LOGGER.trace("Response has already been committed. Unable to redirect to the start page for the user " + authentication.getName());
            return;
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }
}
