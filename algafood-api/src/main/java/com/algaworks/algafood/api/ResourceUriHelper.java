package com.algaworks.algafood.api;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@UtilityClass // anotação do lombok para informar que esta classe é utilitaria, não podendo ser extendida, todos os métodos deve ser estáticos e coloca o final na classe
public class ResourceUriHelper {

    public static void addUriInResponseHeader(Object resourceId){
        URI uri =  ServletUriComponentsBuilder.fromCurrentRequestUri() // Criar uma URI com as informações da requisição atual, obtem protocolo http ou https, dominio, porta.
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader(HttpHeaders.LOCATION, uri.toString()); // atribuir a uri na resposta da requisisao na tag LOCATION
    }
}
