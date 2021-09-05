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

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.v1.model.FormaPagamentoModel;
import com.razzolim.food.api.v1.model.input.FormaPagamentoInput;

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
@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);
    
    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> buscar(
    		@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
    		Long formaPagamentoId,
    		
    		ServletWebRequest request);
    
    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    FormaPagamentoModel adicionar(
    		@ApiParam(name = "corpo", value = "Representa��o de uma nova forma de pagamento", 
    			required = true)
    		FormaPagamentoInput formaPagamentoInput);
    
    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormaPagamentoModel atualizar(
    		@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
    		Long formaPagamentoId,
    		
    		@ApiParam(name = "corpo", value = "Representa��o de uma forma de pagamento com os novos dados", 
    			required = true)
    		FormaPagamentoInput formaPagamentoInput);
    
    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
        @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void remover(
    		@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
    		Long formaPagamentoId);
}