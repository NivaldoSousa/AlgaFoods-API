package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/*
* Classe responsavel por obter as configurações feitas no application.properties
* */
@Validated // Estamos validando a classe apartir de seus atributos tambem com anotações de validdação
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotNull
    private String remetente;

    // Atribuimos FAKE como padrão
    // Isso evita o problema de enviar e-mails de verdade caso você esqueça de definir a propriedade
    private Implementacao impl = Implementacao.FAKE;

    public enum Implementacao {
        SMTP, FAKE
    }
}
