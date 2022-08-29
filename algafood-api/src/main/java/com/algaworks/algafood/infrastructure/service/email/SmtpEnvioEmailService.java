package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender; // para envia um email precisar ser estanciado essa classe

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    /*
    * Monta o email para ser enviado
    * */
    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage(); // Classe que irá conter o corpo e assunto do email
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8"); // Classe auxiliar para montar o MimeMessage colocando o corpo, assunto do email, UTF-8 para nao ter problemas de encode

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0])); //lista contendo os destinatarios para serem enviados
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true); // true pq estamos dizendo que o corpo do email é em HTML

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar e-mail", e);
        }
    }

    private String processarTemplate(Mensagem mensagem){
        try {
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo()); // retorna o um HTML do tipo Template com o corpo do e-mail

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis()); // processa o template passando os objetos java retornando em string o html já transformado
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do e-mail", e);
        }
    }
}
