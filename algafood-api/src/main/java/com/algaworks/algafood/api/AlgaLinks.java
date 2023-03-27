package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.PedidoController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/*
 * Responsavel por criar links
 * */
@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLE = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToPedidos() {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));

        String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();

        return new Link(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLE.concat(filtroVariables)), "pedidos");
    }
}
