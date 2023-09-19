package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freemarkerConfig;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem){
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo()); // retorna o um HTML do tipo Template com o corpo do e-mail

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis()); // processa o template passando os objetos java retornando em string o html já transformado
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do e-mail", e);
        }
    }
}
