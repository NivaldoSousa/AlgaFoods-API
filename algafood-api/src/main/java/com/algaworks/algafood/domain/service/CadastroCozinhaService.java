package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de codigo %d não pode ser removida, pois esta em uso";
	
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(cozinhaId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId)); // um tratamento de exception de negocio
        }
    }
    
    public Cozinha buscarOuFalhar(Long cozinhaId) {
    	return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    	//orElseThrow serve para retorna caso tenha uma cozinha, ou lançar a exceçao.
    }
}
