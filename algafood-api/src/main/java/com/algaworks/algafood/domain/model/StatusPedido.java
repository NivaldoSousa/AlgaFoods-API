package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

    CRIADO("Cridado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    /*
    * StatusPedido... -> varArgs esta sendo usado pois pode ser passado nenhum, 1 ou mais tipos de status
    * */
    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public String getDescricao() {
        return this.descricao;
    }

    /*
    * Verifica se o status atual permiti se alterado para o novo status
    * utilizando o statusAnterior como referencia
    * EX: Alterar para CONFIRMADO somente se o status anterior for CRIADO
    * */
    public boolean naoPodeAlterarPara(StatusPedido novoStatus){
        return !novoStatus.statusAnteriores.contains(this);
    }

    public boolean podeAlterarPara(StatusPedido novoStatus){
        return !naoPodeAlterarPara(novoStatus);
    }
}
