package com.algaworks.algafood.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* Classe responsavel por interceptar as requisições dos endpoint da aplicação para versoes que estão depreciadas
* lançando uma resposta Http 410
* */
@Component
public class ApiRetirementHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().startsWith("/v0")) {
            response.setStatus(HttpStatus.GONE.value()); // Status HTTP 410
            return false;
        }

        return true;
    }
}
