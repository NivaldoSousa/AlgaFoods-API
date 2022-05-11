package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    /*
    * Busca o email do Usuario apartir do email informado
    * a busca do email é feita pelo proprio JPA gera o select apartir da assinatura do metodo.
    * */
    Optional<Usuario> findByEmail(String email);
}
