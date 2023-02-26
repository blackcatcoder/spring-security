package com.spring.security.workaround.security.customfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class PrivateAuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    System.out.println("PrivateAuthenticationFilter request");
    filterChain.doFilter(request, response);
    System.out.println("PrivateAuthenticationFilter response");

  }

}
