package com.algaworks.algafood.infrastructure.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @Autowired
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder(); //criando uma instancia do criteria
        var query = builder.createQuery(VendaDiaria.class); // retorno do tipo da comsulta
        var root = query.from(Pedido.class); // definindo a clausula from do select

        //criando o retorno da função date() do mysql para retorna a data do pedido truncada
        var functionDateDataCriacao = builder.function("date", // nome da função do mysql
                Date.class, // resultado esperado, no caso sera convertido em LocalDate
                root.get("dataCriacao")); // Argumento da expressão, ou seja sera truncada a coluna da tabela Pedido

        //Construção do objeto VendaDiaria apartir da seleção dos atributos da classe Pedido
        //Importante seguir a ordem em que o construtor da classe VendaDiaria está definido na hora de usar o construct
        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")), // quantidade de pedidos feitas na mês
                builder.sum(root.get("valorTotal"))); //somando o valor das vendas por mês

        query.select(selection); // definindo a clausula select
        query.groupBy(functionDateDataCriacao); // definindo a clausula groupBy

        return manager.createQuery(query).getResultList(); // apartir da query montada sera retornado a list de VendaDiaria
    }
}
