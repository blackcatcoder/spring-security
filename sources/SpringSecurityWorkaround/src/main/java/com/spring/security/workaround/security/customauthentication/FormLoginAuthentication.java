package com.spring.security.workaround.security.customauthentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class FormLoginAuthentication extends UsernamePasswordAuthenticationToken {

  public FormLoginAuthentication(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

}
