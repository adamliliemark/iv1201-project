package com.iv1201.project.recruitment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


public class ApplicationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        handle(httpServletRequest, httpServletResponse, authentication);
        LOGGER.trace(authentication.getName() + " has logged in");
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        String targetURL = determineTargetURL(authentication);

        if (response.isCommitted()) {
            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetURL);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetURL);
    }

    protected String determineTargetURL(final Authentication authentication){

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (final GrantedAuthority g:authorities) {
                String authorityName = g.getAuthority();
                if (authorityName.equals("ROLE_ADMIN"))
                    return "/admin";
        }

        return "/home";
    }
}
