package com.example.demo.config;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")
        );
        if (isAdmin)
            setDefaultTargetUrl("/admin");
        else
            setDefaultTargetUrl("/home");
            super.onAuthenticationSuccess(request, response, authentication);
    }
}
