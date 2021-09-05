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

import java.util.List;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
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
@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {
    
    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<FormaPagamentoModel> listar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)
    })
    void desassociar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", 
            response = Problem.class)
    })
    void associar(
            @ApiParam(value = "ID do restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(value = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);
}
