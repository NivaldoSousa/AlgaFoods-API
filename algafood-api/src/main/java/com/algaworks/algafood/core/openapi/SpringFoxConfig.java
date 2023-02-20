package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // Habilita do springFox
public class SpringFoxConfig implements WebMvcConfigurer { //essa interface WebMvcConfigurer é usada para customizar o Spring MVC no projeto

    /*
     * Esse metodo retorna um Docket
     * Docket é uma classe do springFox que representa a configuração da API para gerar definição da especificação OpenAPI
     * ou seja estamos configurando um conjunto de serviços que deve ser documentado.
     *
     * */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //Seleciona qual parte do projeto irá ser escaneado para gerar as definições, RequestHandlerSelectors.basePackage() estamos dizendo para ele escanear somente esse pacote.
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades")); // passando as informações apartir do metodo apiInfo()
    }

    /*
    * Descreve informações da API na documentação, ou seja, na interafce grafica do swagger
    * */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
                .build();
    }
    /*
     * Mapeamento de caminhos para servir arquivos estáticos do swagger
     *
     * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html") //padrão do path de acesso ao html
                .addResourceLocations("classpath:/META-INF/resources/"); //caminho onde estão os arquivos estáticos da dependencia do swagger-ui.

        registry.addResourceHandler("/webjars/**") //padrão do path para obter todos os arquivos estaticos
                .addResourceLocations("classpath:/META-INF/resources/webjars/"); //caminho onde os aqruivos estáticos estão
    }
}
