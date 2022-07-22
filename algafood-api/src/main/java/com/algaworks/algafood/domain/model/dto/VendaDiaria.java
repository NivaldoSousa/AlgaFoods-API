package com.algaworks.algafood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor //criar um construtor com os tres atributos da classe
@Setter
@Getter
public class VendaDiaria {

    private LocalDate data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
