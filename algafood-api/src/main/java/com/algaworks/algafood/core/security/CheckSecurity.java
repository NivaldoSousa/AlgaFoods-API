package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* Anotaçao resposavel por agrupar as anotações de restrições dos recrusos da API
* */
public @interface CheckSecurity {

    /*
    * Responsavel pelas anotações de restrição do grupo de Cozinhas
    * */
    public @interface Cozinhas{
        /*
         * Anotação responsavel por encapsular a anotação @PreAuthorize("isAuthenticated()")
         * com isso mantemos nosso codigo mais legivel e melhor para manuntenção
         * */
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") // Com essa anotação é possivel restrigir o acesso aos endpoints atarves de uma expressão, nesse caso o usuario poderar acessa o recurso somente autenticado
        @Retention(RetentionPolicy.RUNTIME) // Essa anootação vai ser lida em tempo de execução
        @Target(ElementType.METHOD) // Essa anotação vai ser usada em metodos
        public @interface PodeConsultar {
        }

        /*
         * Anotação responsavel por encapsular a anotação @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
         * com isso mantemos nosso codigo mais legivel e melhor para manuntenção
         * */
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')") // Aqui utilizamos a expressão hasAuthority passando a permissão que o usuario deverá ter, somente ela poderar ser permitido a esse recurso
        @Retention(RetentionPolicy.RUNTIME) // Essa anootação vai ser lida em tempo de execução
        @Target(ElementType.METHOD) // Essa anotação vai ser usada em metodos
        public @interface PodeEditar {
        }
    }

    /*
     * Responsavel pelas anotações de restrição do grupo de Restaurantes
     * */
    public @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

    }
}
