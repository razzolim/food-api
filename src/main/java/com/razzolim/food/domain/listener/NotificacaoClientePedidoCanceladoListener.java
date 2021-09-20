/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.razzolim.food.domain.event.PedidoCanceladoEvent;
import com.razzolim.food.domain.model.Pedido;
import com.razzolim.food.domain.service.EnvioEmailService;
import com.razzolim.food.domain.service.EnvioEmailService.Mensagem;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class NotificacaoClientePedidoCanceladoListener {

    private static final String TEMPLATE_PEDIDO_CANCELADO_HTML = "emails/pedido-cancelado.html";
    
    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
                .corpo(TEMPLATE_PEDIDO_CANCELADO_HTML)
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
