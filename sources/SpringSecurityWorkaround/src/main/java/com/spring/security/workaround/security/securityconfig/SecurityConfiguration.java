package com.spring.security.workaround.security.securityconfig;

import com.spring.security.workaround.security.customfilter.IndexAuthenticationFilter;
import com.spring.security.workaround.security.customfilter.PrivateAuthenticationFilter;
import com.spring.security.workaround.security.customfilter.PublicAuthenticationFilter;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

  @Order(2)
  @Bean
  public SecurityFilterChain securityFilterChainPublic(HttpSecurity http) throws Exception {

    http.securityMatcher("/public/**").authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
        .addFilterBefore(new PublicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Order(3)
  @Bean
  public SecurityFilterChain securityFilterChainPrivate(HttpSecurity http) throws Exception {

    http.securityMatcher("/private/**").authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .addFilterAfter(new PrivateAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin(Customizer.withDefaults());

    return http.build();
  }


  @Order(5)
  @Bean
  public SecurityFilterChain securityFilterChainIndex(HttpSecurity http) throws Exception {

    http.securityMatcher("/**").authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
        .addFilterAfter(new IndexAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin(Customizer.withDefaults());

    return http.build();
  }


//  @Order(2)
//  @Bean
//  public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//         .formLogin((form) -> form.loginPage("/oauth/login").permitAll());
//    return http.build();
//  }

//  @Order(3)
//  @Bean
//  public SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//        .formLogin((form) -> form.loginPage("/oauth/signup").permitAll());
//    return http.build();
//  }

  @Bean
  ApplicationListener<AuthenticationSuccessEvent> authSuccess() {
    return event -> {
      var auth = event.getAuthentication();
      LoggerFactory.getLogger(SecurityConfiguration.class).info("LOGIN SUCCESFUL [{}] - {}", auth.getClass().getSimpleName(), auth.getName());
    };
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager(
        User.withUsername("user")
            .password("{noop}user")
            .roles("USER")
            .authorities("ROLE_USER")
            .build(),
        User.withUsername("admin")
            .password("{noop}admin")
            .roles("ADMIN")
            .authorities("ROLE_ADMIN")
            .build(),
        User.withUsername("foo")
            .password("{noop}bar")
            .roles("EVERYBODY")
            .authorities("ROLE_EVERYBODY")
            .build()
    );
  }

//  @Bean
//  public ClientRegistrationRepository clientRegistrationRepository() {
//    return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
//  }
//
//  private ClientRegistration googleClientRegistration() {
//    return ClientRegistration.withRegistrationId("google")
//        .clientId("google-client-id")
//        .clientSecret("google-client-secret")
//        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//        .scope("openid", "profile", "email", "address", "phone")
//        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
//        .tokenUri("https://www.googleapis.com/oauth2/v4/token")
//        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//        .userNameAttributeName(IdTokenClaimNames.SUB)
//        .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//        .clientName("Google")
//        .build();
//  }
}
