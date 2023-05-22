package com.algaworks.algafoodauth.core;

import com.algaworks.algafoodauth.domain.Usuario;
import com.algaworks.algafoodauth.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
* Responsavel por cosultar as informações do usuario usadas pelo OAuth2
* */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail informado"));

        return new AuthUser(usuario);
    }
}
