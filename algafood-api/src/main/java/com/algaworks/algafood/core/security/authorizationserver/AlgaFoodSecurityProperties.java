package com.algaworks.algafood.core.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/*
* Classe de configuração que carrega a url usada na classe AuthorizationServerConfig
* */
@Component
@Getter
@Setter
@ConfigurationProperties("algafood.auth")
public class AlgaFoodSecurityProperties {

    @NotBlank
    private String providerUrl;
}
