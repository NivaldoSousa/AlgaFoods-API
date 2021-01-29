package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        //verifica se tem conteudo na variavel cozinha, pois utilizando o Optional nunca retorna null
        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get()); // sempre que usar o optional chama o get() da variavel pra obter o valor
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

        if (cozinhaAtual.isPresent()) {
            //esse metodo BeanUtils faz a mesma coisa passando os valores de uma variavel para outra
            // a propriedade id nao esta sendo copiada
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
          Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaSalva);
        }
        return ResponseEntity.notFound().build(); // nao encontrado
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
        //O tryCatch serve para capturar exeçao caso o cliente tente excluir algo que viole as regras do banco, como
        // por exemplo relacionamento entre entidades
        try {
            cadastroCozinha.excluir(cozinhaId);
            return ResponseEntity.noContent().build(); // nao ira retorna nada, somente sera excluido com suscesso

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build(); // nao encontrado

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //Retorna um erro de conflito
        }
    }
}
