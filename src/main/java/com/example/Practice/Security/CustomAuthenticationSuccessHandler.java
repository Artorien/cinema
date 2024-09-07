package com.example.Practice.Security;

import com.example.Practice.Review;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        session.setAttribute("username", userDetails.getUsername());

        Set<String> boughtFilms = new HashSet<>();
        session.setAttribute("boughtFilms", boughtFilms);

        byte[] pfpImage = new byte[0];
        session.setAttribute("pfp", pfpImage);

        List<Review> reviews = new ArrayList<>();
        session.setAttribute("reviews", reviews);

        response.sendRedirect("/practice/films");
    }
}