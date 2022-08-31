package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
    @EventListener // essa anotação marca o metodo como um listener ou seja ele reagi ao evento quando for lançado
    public void aoConfirmarPedido(PedidoConfirmadoEvent event){

        Pedido pedido = event.getPedido();

        //Criando o objeto que sera enviado para o serviço de envio de email
        var mesagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html") // nome do arquivo html na pasta templates
                .variavel("pedido", pedido) // map com a variavel que sera usada no html(template) com o objeto Pedido
                .destinatario(pedido.getCliente().getEmail()).build();

        envioEmailService.enviar(mesagem);
    }
}
