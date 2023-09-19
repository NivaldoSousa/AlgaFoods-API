package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/*
* Classe de serviço responsavel por fazer a simulação do envio fazendo o log no console
* */
@Slf4j //Essa anotação cria uma variavel "log", onde podemos usar para informar no console
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private ProcessadorEmailTemplate processadorEmailTemplate;
    @Override
    public void enviar(Mensagem mensagem){
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

}
