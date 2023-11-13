package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class AuthorizationServerConfig {

    /*
     * Método que cuida do HttpSecurity que aplica os filtros de segurança
     * */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    // essa anotação é necessário pois teremos varios FilterChain para o authorizationServer e resourceServer precisamos garantir que o authorizationServer seja inicializado primeiro
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http); // aplica várias configurações padrões de segurança do AuthorizationServer
        return http.build();
    }

    /*
     * Responsavel por escrever qual será o authorization server que irá assinar os tokens
     * */
    @Bean
    public ProviderSettings providerSettings(AlgaFoodSecurityProperties properties) {
        return ProviderSettings.builder().issuer(properties.getProviderUrl()).build();
    }

    /*
     * Guarda os clients do AuthorizationServer
     * */
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        RegisteredClient algafoodbackend = RegisteredClient
                .withId("1")
                .clientId("algafood-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) //Configurando o Token Opaco
                        .accessTokenTimeToLive(Duration.ofMinutes(30)) // Duração da validação do Token
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(Arrays.asList(algafoodbackend)); // adicionando a configuração em memória
    }
}
