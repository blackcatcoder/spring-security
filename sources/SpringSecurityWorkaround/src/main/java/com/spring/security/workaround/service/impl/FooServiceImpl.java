package com.spring.security.workaround.service.impl;

import com.spring.security.workaround.service.FooService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class FooServiceImpl implements FooService {

  public String getImageName(){
    String temp = checkSomething();
    if(temp.equals("ADMIN")){
      return "you can access any where";
    }else{
      return "you are normal user so you can only access public page";
    }
  }

  /*
  - we can get Authentication object here so we don't need to pass from the controller to here
   */
  private String checkSomething(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication.getName().equals("ADMIN")){
      return "admin";
    }else{
      return "normal";
    }
  }

  // only authentication and has role admin can access this resource
  // but we should not protect at this service level, we should protect at controller level
  @PreAuthorize("isAuthenticated() and hashRole('admin')")
  public String getVipResource(){
    return "vip resource";
  }

}
