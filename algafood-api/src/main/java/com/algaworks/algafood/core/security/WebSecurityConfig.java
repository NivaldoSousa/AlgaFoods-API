package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity // Habilita as configurações de segurança da aplicação
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Retira a tela de login e habilita a permissão de qualquer requisição autenticada, caso nao esteja autenticada nao permiti
        http.httpBasic()//Retira a tela de login
                .and()
                .authorizeRequests()
                .antMatchers("/v1/cozinhas/**").permitAll() // Toda url que tem esse padrão será aceito na aplicação
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Mantem a API em estado Stateless, ou seja, durante a sessão não mantem guardados o usuario e senha no servidor.
                .and()
                .csrf().disable(); // csrf significa cross-site request forgery é um tipo de ataque em que o atacante faslsifica a requisição a partir de uma requisição verdadeira atraves do cookies. Dessa forma desabilitamos o uso de cookies.
    }
}
