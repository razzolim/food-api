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

import com.razzolim.food.domain.exception.PedidoNaoEncontradoException;
import com.razzolim.food.domain.model.Pedido;
import com.razzolim.food.domain.repository.PedidoRepository;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
	return pedidoRepository.findById(pedidoId)
		.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
