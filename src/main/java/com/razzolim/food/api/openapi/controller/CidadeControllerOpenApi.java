/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.openapi.controller;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.model.CidadeModel;
import com.razzolim.food.api.model.input.CidadeInput;

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
@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inv√°lido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade n√£o encontrada", response = Problem.class) })
    public CidadeModel buscar(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    		Long cidadeId);

    @ApiOperation("Adiciona uma cidade")
    @ApiResponses({ 
        @ApiResponse(code = 201, message = "Cidade cadastrada") })
    public CidadeModel adicionar(
    		@ApiParam(name = "corpo", value = "RepresentaÁ„o de uma nova cidade", required = true)
    		CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({ 
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade n√£o encontrada", response = Problem.class) })
    public CidadeModel atualizar(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
    		Long cidadeId,
    		
    		@ApiParam(name = "corpo", value = "RepresentaÁ„o de uma cidade com os novos dados")
    		CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({ 
        @ApiResponse(code = 204, message = "Cidade exclu√≠da"),
        @ApiResponse(code = 404, message = "Cidade n√£o encontrada", response = Problem.class) })
    public void remover(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    		Long cidadeId);

}
