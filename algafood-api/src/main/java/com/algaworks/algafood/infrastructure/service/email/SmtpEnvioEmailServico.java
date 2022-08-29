package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailServico implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender; // para envia um email precisar ser estanciado essa classe

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage(); // Classe que irá conter o corpo e assunto do email
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8"); // Classe auxiliar para montar o MimeMessage colocando o corpo, assunto do email, UTF-8 para nao ter problemas de encode

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0])); //lista contendo os destinatarios para serem enviados
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true); // true pq estamos dizendo que o corpo do email é em HTML

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar e-mail", e);
        }
    }
}
