package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO = "Estado de codigo %d não pode ser removido, pois está em uso";
    
    private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
    
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado buscarOuFalhar(Long estadoId) {
    	return estadoRepository.findById(estadoId)
    			.orElseThrow(() -> new EstadoNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
    }
    
    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);

        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradaException(
                    String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
}
