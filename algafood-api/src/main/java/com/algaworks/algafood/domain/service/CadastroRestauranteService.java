package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;
  
    @Transactional // Essa anotaçao abre transação com banco de dados, permitindo salva, excluir e updates etc
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();

		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}
    
    //Nesse método nao tem a necessidade de ter o método save salvando o restauranteAtual
    //pois com o @Transactional quando vc faz uma busca em uma entidade o JPA gerencia esse objeto,
    //entao quando se faz um setAtivo() ou qualquer outro set como por exemplo setNome()(esta setando o valor no metodo ativar) informando um valor,
    //esse objeto é atualizado no banco de dados sem a necessidade do save.
    @Transactional
    public void ativar(Long restauranteId) {
    	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
    	restauranteAtual.ativar();
    }
    
    //Nesse método nao tem a necessidade de ter o método save salvando o restauranteAtual
    //pois com o @Transactional quando vc faz uma busca em uma entidade o JPA gerencia esse objeto,
    //entao quando se faz um setAtivo() ou qualquer outro set como por exemplo setNome() (esta setando o valor no metodo inativar) informando um valor,
    //esse objeto é atualizado no banco de dados sem a necessidade do save.
    @Transactional
    public void inativar(Long restauranteId) {
    	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
    	restauranteAtual.inativar();
    }
    
    public Restaurante buscarOuFalhar(Long restauranteId) {
    	return restauranteRepository.findById(restauranteId)
    			.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}