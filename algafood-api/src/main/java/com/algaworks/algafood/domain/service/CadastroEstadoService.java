package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO = "Estado de codigo %d não pode ser removido, pois está em uso";

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradaException(estadoId));
	}

	@Transactional
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			//O flush serve para comitar a operação no caso deleteById na hora da execução
			//como o metodo esta anotado com o @Transactional, caso de um execption nao consiguiremos capturar e tratar
			//retornando uma execption customizada, com isso o flush resolver esse problema.
			estadoRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}
