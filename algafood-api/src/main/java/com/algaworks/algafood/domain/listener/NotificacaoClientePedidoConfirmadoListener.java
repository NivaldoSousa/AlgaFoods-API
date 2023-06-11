package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/*
* Classe responsavel por reagir a um evento quando disparado
* */
@Component
public class NotificacaoClientePedidoConfirmadoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    /*
    * Responsavel reagir ao evento apos o save do Pedido fazendo o envio do e-mail
    * */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) // será processado o evento apos o commit no banco  phase = TransactionPhase.BEFORE_COMMIT -> será concluido o processamento do evento caso ambos os metodos save e aoConfirmarPedido seja concluidos com sucesso
    public void aoConfirmarPedido(PedidoConfirmadoEvent event){

        Pedido pedido = event.getPedido();

        //Criando o objeto que sera enviado para o serviço de envio de email
        var mesagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("emails/pedido-confirmado.html") // nome do arquivo html na pasta templates
                .variavel("pedido", pedido) // map com a variavel que sera usada no html(template) com o objeto Pedido
                .destinatario(pedido.getCliente().getEmail()).build();

        envioEmailService.enviar(mesagem);
    }
}
