package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
}
