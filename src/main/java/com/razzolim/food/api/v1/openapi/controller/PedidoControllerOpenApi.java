/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.v1.model.PedidoModel;
import com.razzolim.food.api.v1.model.PedidoResumoModel;
import com.razzolim.food.api.v1.model.input.PedidoInput;
import com.razzolim.food.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({
	    @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
	            name = "campos", paramType = "query", type = "string")
	})
	PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);  
    
    @ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    PedidoModel adicionar(
    		@ApiParam(name = "corpo", value = "Representa��o de um novo pedido", required = true)
    		PedidoInput pedidoInput);
    
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoModel buscar(
    		@ApiParam(value = "C�digo de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
    			required = true)
    		String codigoPedido);
}
