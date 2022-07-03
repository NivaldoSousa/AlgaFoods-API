package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpec {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
        return (root, query, builder) -> {

            //Verifica se a consulta é do tipo Pedido pois se for do tipo numerico por causa do count do pageble nao entra na condição evitando uma exception
            if(Pedido.class.equals(query.getResultType())) {
                //atribuindo o fetch para nao ter prblemas de muitos selects sendo feitos para retornar os registros
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }
            var predicates = new ArrayList<Predicate>(); // criando um array de Predicates para add os mapeamento dos atributos

            if(filtro.getClienteId() != null){
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId())); //mapeando o atributo da classe PedidoFilter com o atributo da classe Pedido
            }

            if(filtro.getRestauranteId() != null){
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if(filtro.getDataCriacaoInicio() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio())); // dataCriacaoInicio tem quer ser maior que dataCriacao
            }

            if(filtro.getDataCriacaoFim() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim())); // dataCriacaoFim tem quer ser menor que dataCriacao
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
