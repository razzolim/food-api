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
import com.razzolim.food.domain.repository.PedidoRepository;

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
    private PedidoRepository pedidoRepository;
    
    @Transactional
    public void confirmar(String codigoPedido) {
	Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	pedido.confirmar();
	
	/* nessa implementacao do spring data em que adiciona evento e dps publica precisa chamar 
	 * o save do repository e esse repository precisa ser do spring data, senão não funciona.*/
	pedidoRepository.save(pedido);
	
	/* dessa forma, quando save for chamado, é feito o flush (descarregar pro bd) e, nesse momento,
	 * os eventos que estão registrados na "fila" serão disparados. O(s) componente que estiverem
	 * escutando esse evento entrarão em ação...
	 */
    }
    
    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.cancelar();
        
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.entregar();
    }

}
