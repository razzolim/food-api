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

import org.springframework.hateoas.CollectionModel;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.v1.model.CidadeModel;
import com.razzolim.food.api.v1.model.input.CidadeInput;

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
	
	@ApiOperation("Lista as cidades")
    public CollectionModel<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    CidadeModel buscar(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    		Long cidadeId);

    @ApiOperation("Adiciona uma cidade")
    @ApiResponses({ 
        @ApiResponse(code = 201, message = "Cidade cadastrada") })
    CidadeModel adicionar(
    		@ApiParam(name = "corpo", value = "Representa��o de uma nova cidade", required = true)
    		CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({ 
        @ApiResponse(code = 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    CidadeModel atualizar(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
    		Long cidadeId,
    		
    		@ApiParam(name = "corpo", value = "Representa��o de uma cidade com os novos dados")
    		CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({ 
        @ApiResponse(code = 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class) })
    void remover(
    		@ApiParam(value = "ID de uma cidade", example = "1", required = true)
    		Long cidadeId);

}
