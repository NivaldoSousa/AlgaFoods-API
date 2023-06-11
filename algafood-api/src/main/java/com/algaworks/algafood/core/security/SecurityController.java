package com.algaworks.algafood.core.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Classe responsavel por retorna a pagina HTML do login do fluxo
 * */
@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }
}
