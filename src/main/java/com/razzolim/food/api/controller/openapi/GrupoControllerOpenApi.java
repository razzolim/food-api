/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.controller.openapi;

import java.util.List;

import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.model.GrupoModel;
import com.razzolim.food.api.model.input.GrupoInput;

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
@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public List<GrupoModel> listar();
    
    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);
    
    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    public GrupoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
            GrupoInput grupoInput);
    
    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel atualizar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId,
            
            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoInput grupoInput);
    
    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public void remover(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);
    
}
