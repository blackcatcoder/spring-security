package com.spring.security.workaround.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("oauth")
public class OAuthController {

  @GetMapping("login")
  public String login(){
    return "login";
  }

  @PostMapping("login")
  public String loginPost(){
    System.out.println("login post");
    return "login";
  }

  @GetMapping("signup")
  public String signup(){
    return "signup";
  }

  @GetMapping("logout")
  public String logout(){
    return "login";
  }

  @GetMapping("logoutsuccess")
  public String logoutsuccess(){
    return "logoutsuccess";
  }
}
