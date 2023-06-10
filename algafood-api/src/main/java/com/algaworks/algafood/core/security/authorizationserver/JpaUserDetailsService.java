package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/*
* Responsavel por cosultar as informações do usuario usadas pelo OAuth2
* */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true) // dessa forma evitamos que a transação feche na hora da chamada do método getAuthorities
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail informado"));

        return new AuthUser(usuario, this.getAuthorities(usuario));
    }

    /*
    * A partir do usuario montar a coleção de permissoes
    * */
    private Collection<GrantedAuthority> getAuthorities(Usuario usuario){
        return usuario.getGrupos().stream()
                .flatMap(grupo -> grupo.getPermissoes().stream())
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase()))
                .collect(Collectors.toSet()); // retornando uma coleçao de set evitamos ter permissoes duplicadas
    }
}
