package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
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
        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()") // Com essa anotação é possivel restrigir o acesso aos endpoints atarves de uma expressão, nesse caso o usuario poderar acessa o recurso somente autenticado
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

        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciarCadastro { }

        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de Pedidos
     * */
    public @interface Pedidos {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        //@PostAuthorize -> Essa anotação faz a validação de segurança apos a execução do metodo, ele tem a mesma função da anotação @PreAuthorize so que executado em momentos diferentes
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or" +
                "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or" +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)") // returnObject é o objeto de retorno onde a anotação PodeBuscar está sendo usada no método
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeBuscar { }


        @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodePesquisar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeCriar { }

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeGerenciarPedidos {
        }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de FormasPagamento
     * */
    public @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de Cidades
     * */
    public @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de Estados
     * */
    public @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar {
        }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de UsuariosGruposPermissoes
     * */
    public @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarPropriaSenha { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeAlterarUsuario { }

        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeEditar { }


        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

    }

    /*
     * Responsavel pelas anotações de restrição do grupo de Estatisticas
     * */
    public @interface Estatisticas {

        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface PodeConsultar { }

    }
}
