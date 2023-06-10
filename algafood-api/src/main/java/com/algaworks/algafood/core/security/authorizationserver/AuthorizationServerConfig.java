package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
// estamos habilitando o AuthorizationServer nesse projeto que seria por exemplo os endpoint como /token, /auth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource); // Irá da permissão(token) ao client caso ela seja cadastrado no banco de dados
    }

    /*
    * A Classe JwtAccessTokenConverter converte as informações do usuário logado em JWT, vice e versa
    */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwtAccessTokenConverter.setSigningKey("hdkjfsjkfhkjshfsfhhfshfshfhshfuishfishifhsihfishuf"); //Chave secreta utilizada para decodificar o token e asssim gerar novos Token de Acesso

        var keyStorePass = jwtKeyStoreProperties.getPassword();; // senha para acessar o arquivo jks
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias(); // idetificador do par da chave

        var keyStoreKeyFactoty = new KeyStoreKeyFactory(jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray()); //obtem as informações do arquivo
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

        // Instanciando uma lista de TokenEnhancers
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) // necessário para utilizar o refresh token
                .reuseRefreshTokens(false) // não permitir utilizar o refresh token mais de um vez
                .accessTokenConverter(jwtAccessTokenConverter()) // Configuramos o conversor de Access Token em JWT
                .tokenEnhancer(enhancerChain) // configurando o Claims
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
