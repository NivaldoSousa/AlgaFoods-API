package com.algaworks.algafoodauth.core;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/*
* Responsavel por adicionar claims customizadas.
* Claims são informações que julgamos ser importante ter no token como por exemplo: id do cliente, email, nome de usuario
* mas lembrando que não é aconselhavel informar senha ou qlq dado sensivel.
*
* */
public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    /*
    * Método responsavel por adicionar as informações ao token antes do token ser de fato criado e assinado.
    * */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        if(oAuth2Authentication.getPrincipal() instanceof AuthUser) {

            var authUser = (AuthUser) oAuth2Authentication.getPrincipal();

            var info = new HashMap<String, Object>();
            info.put("nome_completo", authUser.getFullName());
            info.put("usuario_id", authUser.getUserId());

            var accessToken = (DefaultOAuth2AccessToken) oAuth2AccessToken;
            accessToken.setAdditionalInformation(info); // adiciona as informações que configuramos no token
        }
        return oAuth2AccessToken;
    }
}
