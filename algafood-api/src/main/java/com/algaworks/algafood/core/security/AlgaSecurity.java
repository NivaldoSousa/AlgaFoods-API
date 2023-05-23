package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    /*
    * Retorna o Token da requisição
    * */
    public Authentication getAuthentication(){
        //a partir do SecurityContextHolder temos o contexto do Authentication onde temos o token da requsição
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /*
    * Retorna o id do usuario pelo token
    * */
    public Long getUsuarioId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("usuario_id"); // pegamos o atributo pela chave do Token, nesse nosso caso seria a chave que definimos na classe JwtCustomClaimsTokenEnhancer
    }
}
