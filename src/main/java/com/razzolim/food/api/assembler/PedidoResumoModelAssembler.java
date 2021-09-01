/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.FoodLinks;
import com.razzolim.food.api.controller.PedidoController;
import com.razzolim.food.api.model.PedidoResumoModel;
import com.razzolim.food.domain.model.Pedido;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class PedidoResumoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(foodLinks.linkToPedidos());
        
        pedidoModel.getRestaurante().add(
        		foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(foodLinks.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }
}
