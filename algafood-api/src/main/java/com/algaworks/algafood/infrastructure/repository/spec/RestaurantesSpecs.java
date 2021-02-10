package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
//Consultas com especificaçoes (Specifications)
public class RestaurantesSpecs {

    public static Specification<Restaurante> comFreteGratis() {
        //Expressao Lambda passando os parametros
        return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

}
