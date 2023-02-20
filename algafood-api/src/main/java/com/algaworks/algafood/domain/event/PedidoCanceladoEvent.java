package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * Classe que representa um evento de um pedido cancelado
 * */
@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

    private Pedido pedido;
}