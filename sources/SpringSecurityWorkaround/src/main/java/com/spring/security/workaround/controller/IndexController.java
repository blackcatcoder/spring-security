package com.spring.security.workaround.controller;


import java.security.Principal;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @GetMapping
  public String index(){

   // DefaultSecurityFilterChain

    return "index";
  }

  @GetMapping("index")
  public String indexHome(){

    // DefaultSecurityFilterChain

    return "index";
  }

  @GetMapping("about")
  public String about(){



    return "about";
  }

  @GetMapping("contact")
  public String contact(){
    return "contact";
  }

  @GetMapping("vip")
  public String vip(Authentication authentication, Model model){
    model.addAttribute("userId", authentication.getName());
    String email = Optional.of(authentication.getPrincipal())
        .filter(OidcUser.class::isInstance)
        .map(OidcUser.class::cast)
        .map(OidcUser::getEmail)
        .orElseGet(authentication::getName);
    model.addAttribute("email", email);
    return "vip";
  }

  @GetMapping("error")
  public String error(){
    return "error";
  }

  // note 1
  // do not do use Principal -> because Principal here is a part of java
  @GetMapping("test1")
  public String test1(Principal principal){
    return "test";
  }

  // instead of that you should use Authentication -> because this is a part of spring security
  @GetMapping("test2")
  public String test2(Authentication principal){
    return "test";
  }

  /*
    - we can get Authentication object from above way("/vip") and this way
   */
  @GetMapping("securityContextTest")
  public String securityContextTest(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = authentication.getName();

    return "test";
  }



}
