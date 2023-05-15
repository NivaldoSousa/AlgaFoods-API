package com.algaworks.algafoodauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
// estamos habilitando o AuthorizationServer nesse projeto que seria por exemplo os endpoint como /token, /auth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("algafood-web") // novo client
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token") // tipo de fluxo que esse client ira usar
                .scopes("write", "read")
                .accessTokenValiditySeconds(6 * 60 * 60) // tempo de expiração do access token, nesse exemplo ele vai expirar em 6h. Padrão é 12h
                .refreshTokenValiditySeconds(8 * 60 * 60) // tempo de expiração do refresh token, nesse exemplo ele vai expirar em 8h. Padrão é 30 dias

                .and()
                .withClient("foodnanalytics") // novo client
                .secret(passwordEncoder.encode("food123"))
                .authorizedGrantTypes("authorization_code") // tipo de fluxo que esse client ira usar
                .scopes("write", "read")
                .redirectUris("http://aplicacao-cliente") // URL que precisa ser cadastrada no fluxo Authorization Code Grant

                .and()
                .withClient("faturamento") // novo client
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials") // tipo de fluxo que esse client ira usar
                .scopes("read")

                //Configurando o fluxo Implicit Grant (Esse fluxo não é recomendado o seu uso)
                .and()
                .withClient("webadmin") // novo client
                .authorizedGrantTypes("implicit") // tipo de fluxo que esse client ira usar
                .scopes("write", "read")
                .redirectUris("http://aplicacao-cliente") // URL que precisa ser cadastrada no fluxo Implicit Grant
                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("check123"));
    }

    /*
    * A Classe JwtAccessTokenConverter converte as informações do usuário logado em JWT, vice e versa
    */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwtAccessTokenConverter.setSigningKey("hdkjfsjkfhkjshfsfhhfshfshfhshfuishfishifhsihfishuf"); //Chave secreta utilizada para decodificar o token e asssim gerar novos Token de Acesso

        var jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
        var keyStorePass = jwtKeyStoreProperties.getPassword();; // senha para acessar o arquivo jks
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias(); // idetificador do par da chave

        var keyStoreKeyFactoty = new KeyStoreKeyFactory(jksResource, keyStorePass.toCharArray()); //obtem as informações do arquivo
        var keyPair = keyStoreKeyFactoty.getKeyPair(keyPairAlias); // obtem o valor da chave pelo idetificador do par da chave

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //security.checkTokenAccess("isAuthenticated()"); //permitir usar o endpoint oauth/check_token informando os dados do client
        security.checkTokenAccess("permitAll()") //permitir usar o endpoint oauth/check_token sem a necessidade de informar os dados do client
                .tokenKeyAccess("permitAll()") // permitindo todos acesso apartir da url tokenKey
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) // necessário para utilizar o refresh token
                .reuseRefreshTokens(false) // não permitir utilizar o refresh token mais de um vez
                .accessTokenConverter(jwtAccessTokenConverter()) // Configuramos o conversor de Access Token em JWT
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }

    /*
    * Habilitar na tela de autorização as opções de permissao do escopo, por exemplo, leitura e escrita
    * de acesso aos recursos do Resource Server.
    * */
    private ApprovalStore approvalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    /*
    * Retorna uma instancia de tokenGranter com PKCE
    * */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}
