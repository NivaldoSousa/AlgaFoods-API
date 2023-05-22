package com.algaworks.algafoodauth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /*
    * Busca o email do Usuario apartir do email informado
    * a busca do email é feita pelo proprio JPA gera o select apartir da assinatura do metodo.
    * */
    Optional<Usuario> findByEmail(String email);
}
