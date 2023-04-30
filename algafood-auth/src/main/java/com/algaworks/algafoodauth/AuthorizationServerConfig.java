package com.algaworks.algafoodauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer // estamos habilitando o AuthorizationServer nesse projeto que seria por exemplo os endpoint como /token, /auth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("algafood-web")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token") // tipo de fluxo que esse client ira usar
                .scopes("write", "read")
                .accessTokenValiditySeconds(6 * 60 * 60) // tempo de expiração do access token, nesse exemplo ele vai expirar em 6h. Padrão é 12h
                .refreshTokenValiditySeconds(8 * 60 * 60) // tempo de expiração do refresh token, nesse exemplo ele vai expirar em 8h. Padrão é 30 dias

                .and()
                .withClient("foodnanalytics")
                .secret(passwordEncoder.encode("food123"))
                .authorizedGrantTypes("authorization_code") // tipo de fluxo que esse client ira usar
                .scopes("write", "read")
                .redirectUris("http://aplicacao-cliente") // URL que precisa ser cadastrada no fluxo Authorization Code Grant

                .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials") // tipo de fluxo que esse client ira usar
                .scopes("read")

                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("check123"));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //security.checkTokenAccess("isAuthenticated()"); //permitir usar o endpoint oauth/check_token informando os dados do client
        security.checkTokenAccess("permitAll()"); //permitir usar o endpoint oauth/check_token sem a necessidade de informar os dados do client
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) // necessário para utilizar o refresh token
                .reuseRefreshTokens(false); // não permitir utilizar o refresh token mais de um vez
    }
}
