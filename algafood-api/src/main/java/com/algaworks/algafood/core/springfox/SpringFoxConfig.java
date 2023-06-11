package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.*;
import com.algaworks.algafood.api.v1.model.input.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.model.*;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
    public Docket apiDocketV1() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //Seleciona qual parte do projeto irá ser escaneado para gerar as definições, RequestHandlerSelectors.basePackage() estamos dizendo para ele escanear somente esse pacote.
                .paths(PathSelectors.ant("/v1/**")) // filtra os paths dos controladores para serem exibidos no swagger apenas aqueles que começam com "/v1/"
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
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModel.class),
                        CidadesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                        UsuariosModelOpenApi.class))

                .securitySchemes(Arrays.asList(this.securityScheme()))
                .securityContexts(Arrays.asList(this.securityContext()))

                .apiInfo(apiInfoV1())
                .tags(new Tag("Cidades", "Gerencia as cidades"), // passando as informações apartir do metodo apiInfo()
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões"));
    }

    /*
     * Esse metodo retorna um Docket
     * Docket é uma classe do springFox que representa a configuração da API para gerar definição da especificação OpenAPI
     * ou seja estamos configurando um conjunto de serviços que deve ser documentado.
     *
     * */
    @Bean
    public Docket apiDocketV2() {

        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")) //Seleciona qual parte do projeto irá ser escaneado para gerar as definições, RequestHandlerSelectors.basePackage() estamos dizendo para ele escanear somente esse pacote.
                .paths(PathSelectors.ant("/v2/**")) // filtra os paths dos controladores para serem exibidos no swagger apenas aqueles que começam com "/v1/"
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
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
                        CozinhasModelV2OpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
                        CidadesModelV2OpenApi.class))
                .apiInfo(apiInfoV2())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"));
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("AlgaFood")
                .grantTypes(this.grantTypes())
                .scopes(this.scopes())
                .build();
    }

    private List<GrantType> grantTypes() {
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes(){
        return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    private SecurityContext securityContext(){
        var securityReference = SecurityReference.builder()
                .reference("AlgaFood")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any())
                .build();
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
    public ApiInfo apiInfoV1(){

        return new ApiInfoBuilder()
                .title("AlgaFood API (Depreciada)")
                .description("API aberta para clientes e restaurantes.<br>"
                + "<strong>Essa versão da API está depreciada e deixará de existir a partir de 01/05/2023. "
                + "Use a versão mais atual da API")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
                .build();
    }

    /*
     * Descreve informações da API na documentação, ou seja, na interafce grafica do swagger
     * */
    public ApiInfo apiInfoV2(){

        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
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
