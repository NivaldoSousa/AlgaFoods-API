package com.algaworks.algafood.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/*
 * WebMvcConfigurer -> Interface do Spring MVC qe define metodos de callback para customizar o Spring MVC
 * */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ApiRetirementHandler apiDeprecationHandler;

    /*
     * Habilita o CORS globalmente no projeto
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // habilita qualquer URL
                .allowedMethods("*"); // Habilita os tipos de metodos que serão liberados ex: POST, GET, PUT
               // .allowedOrigins("*") // Habilita url

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiDeprecationHandler);
    }

    /*
    * Ao receber uma requisição é retornado uma resposta contendo um hash (Etag) no cabelhaço da resposta
    * e tambem é verificado se o hash coincide com o hash do "If-None-Match" que é enviado pela requisição
    * caso seja igual ele retorna o status 304 not modified, se nao for igual ele retorna uma nova etag com a nova resposta
    * */
    @Bean
    public Filter shallowEtagHeaderFilter(){
        return new ShallowEtagHeaderFilter();
    }
}
