package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    //@RequestParam é o parametro que vai ser passado pelo usuario
    //"nome" é o parametro vai ser atribuido ao JPQL da query
   /* @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
        return cozinhaRepository.consultarPorNome(nome);
    }*/

}
