package com.spring.security.workaround.security.authenticationprovider;

import com.spring.security.workaround.security.customauthentication.RobotAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RobotAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return RobotAuthentication.class.isAssignableFrom(authentication);
  }

}
