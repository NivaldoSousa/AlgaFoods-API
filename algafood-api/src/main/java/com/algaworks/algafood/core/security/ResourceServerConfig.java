package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity // Habilita as configurações de segurança da aplicação
@EnableGlobalMethodSecurity(prePostEnabled = true) // Habilita a segurança de métodos(endpoints) da aplicação
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().loginPage("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable()
                .cors().and() // Necessario habilitar o CORS quando add spring-security na aplicação
                .oauth2ResourceServer().jwt() //habilitando o JWT
                .jwtAuthenticationConverter(this.jwtAuthenticationConverter()); // De acordo com a permissoes do usuario permiti o acesso aos recursos da API
    }

    /*
    * Retorna a lista de permissoes que usuario possui atraves do token
    * */
    private JwtAuthenticationConverter jwtAuthenticationConverter(){

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if(authorities == null){
                authorities = Collections.emptyList(); // Caso o usuário não tenha nenhuma permissão retorna uma coleção vazia
            }

            //Obtendo os escopos do token
            var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

            //Adicionando a colection de escopos a coleção de authorities(permissões)
            grantedAuthorities.addAll(authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));

            return grantedAuthorities;
        });
        return jwtAuthenticationConverter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
