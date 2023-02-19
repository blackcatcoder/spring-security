package com.spring.security.workaround.security.customfilter;

import com.spring.security.workaround.security.customauthentication.RobotAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/*
- only apply for robot should not for every other login method

 */

public class RobotAuthenticationFilter extends OncePerRequestFilter {

  private final String HEADER_NAME = "x-robot-password";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {


    // should we execute filter
    // if not in case of robot -> allow request go to next filter
    if(!Collections.list(request.getHeaderNames()).contains(HEADER_NAME)){
      System.out.println("not robot case");
      filterChain.doFilter(request, response);
      return;
    }


    // 1: authentication
    System.out.println("hello robot filter");

    // curl localhost:8080/vip -H "x-robot-password: hello" -v
    var password = request.getHeader("x-robot-password");
    if(!"hello".equals(password)){
      System.out.println("password is invalid");
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setCharacterEncoding("utf-8");
      response.setHeader("Content-type", "text/plain;charset=utf-8");
      response.getWriter().println("you are not mr robot");
      return;
    }

    System.out.println("password is valid");
    var newContext = SecurityContextHolder.createEmptyContext();
    newContext.setAuthentication(new RobotAuthentication());
    SecurityContextHolder.setContext(newContext);

    // 2: do the rest
    filterChain.doFilter(request, response);
  }

}
