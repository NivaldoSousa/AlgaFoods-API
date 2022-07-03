package com.algaworks.algafood.domain.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Setter
@Getter
public class PedidoFilter {

    private Long clienteId;

    private Long restauranteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Informa o tipo de formato que tera a data ex: "2000-10-31T01:30:00.000-05:00"
    private OffsetDateTime dataCriacaoInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // Informa o tipo de formato que tera a data ex: "2000-10-31T01:30:00.000-05:00
    private OffsetDateTime dataCriacaoFim;
}
