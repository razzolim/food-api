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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.razzolim.food.domain.event.PedidoConfirmadoEvent;
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
public class NotificacaoClientePedidoConfirmadoListener {
    
    private static final String TEMPLATE_PEDIDO_CONFIRMADO_HTML = "pedido-confirmado.html";
    
    @Autowired
    private EnvioEmailService envioEmailService;
    
    @EventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        
        Pedido pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                        .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                        .corpo(TEMPLATE_PEDIDO_CONFIRMADO_HTML)
                        .variavel("pedido", pedido)
                        .destinatario(pedido.getCliente().getEmail())
                        .build();
                
                envioEmailService.enviar(mensagem);
    }
    
}
