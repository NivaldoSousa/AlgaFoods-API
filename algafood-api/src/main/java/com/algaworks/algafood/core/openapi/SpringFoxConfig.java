package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.PedidoResumoModel;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.openapi.model.PedidosResumoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2 // Habilita do springFox
@Import(BeanValidatorPluginsConfiguration.class) // habilita a classe de configuração do bean validation para o springFox
public class SpringFoxConfig implements WebMvcConfigurer { //essa interface WebMvcConfigurer é usada para customizar o Spring MVC no projeto

    /*
     * Esse metodo retorna um Docket
     * Docket é uma classe do springFox que representa a configuração da API para gerar definição da especificação OpenAPI
     * ou seja estamos configurando um conjunto de serviços que deve ser documentado.
     *
     * */
    @Bean
    public Docket apiDocket() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //Seleciona qual parte do projeto irá ser escaneado para gerar as definições, RequestHandlerSelectors.basePackage() estamos dizendo para ele escanear somente esse pacote.
                .build()
                .useDefaultResponseMessages(false) // retira os status de erro padrão da documentação
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages()) // criar de forma global a resposta da API para todos os metodos GET
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages()) // criar de forma global a resposta da API para todos os metodos POST
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages()) // criar de forma global a resposta da API para todos os metodos PUT
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages()) // criar de forma global a resposta da API para todos os metodos DELETE
                .additionalModels(typeResolver.resolve(Problem.class)) // adiciona um model extra nas configurações do swagger
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,  // Ignora a classe que está sendo tipada pelo controller na documentação
                        Resource.class, File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // esse metodo substitui a classe original pela classe especificada, isso é com a finlaidade apenas para a documentação
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"), // passando as informações apartir do metodo apiInfo()
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"));
    }

    /*
     * Criar uma lista de retornos padroes da API para todos os metogos POST e PUT
     * */
    private List<ResponseMessage> globalPostPutResponseMessages() {

        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema")) // referencia um modelo de representação em caso de erro, onde pegamos o nome da classe apartir da anotação @ApiModel
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema")) // referencia um modelo de representação em caso de erro, onde pegamos o nome da classe apartir da anotação @ApiModel
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()) //code 415
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .responseModel(new ModelRef("Problema")) // referencia um modelo de representação em caso de erro, onde pegamos o nome da classe apartir da anotação @ApiModel
                        .build()
        );
    }

    /*
     * Criar uma lista de retornos padroes da API para todos os metogos DELETE
     * */
    private List<ResponseMessage> globalDeleteResponseMessages() {

        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema")) // referencia um modelo de representação em caso de erro, onde pegamos o nome da classe apartir da anotação @ApiModel
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema")) // referencia um modelo de representação em caso de erro, onde pegamos o nome da classe apartir da anotação @ApiModel
                        .build()
        );
    }

    /*
    * Criar uma lista de retornos padroes da API para todos os metogos GET
    * */
    private List<ResponseMessage> globalGetResponseMessages(){

        return Arrays.asList(new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()) //code 500
                .message("Erro interno do servidor")
                .build(), new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value()) //code 406
                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build());
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
