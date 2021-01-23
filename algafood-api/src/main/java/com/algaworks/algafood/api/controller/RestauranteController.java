package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listar(){
        return restauranteRepository.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteRepository.buscar(restauranteId);

        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
