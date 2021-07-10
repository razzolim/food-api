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

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.model.Pedido;
import com.razzolim.food.domain.model.StatusPedido;

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
    
    @Transactional
    public void confirmar(Long pedidoId) {
	Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
	
	if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
	    throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s.", 
		    pedido.getId(), pedido.getStatus().getDescricao(), 
		    StatusPedido.CONFIRMADO.getDescricao()));
	}
	
	pedido.setStatus(StatusPedido.CONFIRMADO);
	pedido.setDataConfirmacao(OffsetDateTime.now());
    }
    
    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        
        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(), pedido.getStatus().getDescricao(), 
                            StatusPedido.CANCELADO.getDescricao()));
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        
        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(), pedido.getStatus().getDescricao(), 
                            StatusPedido.ENTREGUE.getDescricao()));
        }
        
        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }

}
