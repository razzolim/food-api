/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.model.Pedido;
import com.razzolim.food.domain.service.EnvioEmailService.Mensagem;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Service
public class FluxoPedidoService {
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private EnvioEmailService envioEmailService;
    
    @Transactional
    public void confirmar(String codigoPedido) {
	Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	pedido.confirmar();
	
	var mensagem = Mensagem.builder()
	        .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
	        .corpo("pedido-confirmado.html")
	        .variavel("pedido", pedido)
	        .destinatario(pedido.getCliente().getEmail())
	        .build();
	
	envioEmailService.enviar(mensagem);
    }
    
    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

}
