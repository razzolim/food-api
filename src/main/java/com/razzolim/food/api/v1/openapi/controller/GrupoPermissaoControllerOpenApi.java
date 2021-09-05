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
import com.razzolim.food.api.v1.model.PermissaoModel;

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
public interface GrupoPermissaoControllerOpenApi {
    
    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    List<PermissaoModel> listar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId);

    @ApiOperation("Desassociação de permissão com grupo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", 
            response = Problem.class)
    })
    void desassociar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(value = "ID da permissão", example = "1", required = true)
            Long permissaoId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
        @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada", 
            response = Problem.class)
    })
    void associar(
            @ApiParam(value = "ID do grupo", example = "1", required = true)
            Long grupoId,
            
            @ApiParam(value = "ID da permissão", example = "1", required = true)
            Long permissaoId);
}
