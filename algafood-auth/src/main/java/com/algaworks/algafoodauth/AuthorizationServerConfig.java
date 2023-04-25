package com.algaworks.algafoodauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer // estamos habilitando o AuthorizationServer nesse projeto que seria por exemplo os endpoint como /token, /auth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

}
