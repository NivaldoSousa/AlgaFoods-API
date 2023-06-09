package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    /*
     * Retorna o Token da requisição
     * */
    public Authentication getAuthentication() {
        //a partir do SecurityContextHolder temos o contexto do Authentication onde temos o token da requsição
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /*
     * Retorna o id do usuario pelo token
     * */
    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("usuario_id"); // pegamos o atributo pela chave do Token, nesse nosso caso seria a chave que definimos na classe JwtCustomClaimsTokenEnhancer
    }

    /*
     * Verifica se o usuario da requisição é dono do resturante
     * */
    public boolean gerenciaRestaurante(Long restauranteId) {
        return restauranteRepository.existsResponsavel(restauranteId, this.getUsuarioId());
    }
}
