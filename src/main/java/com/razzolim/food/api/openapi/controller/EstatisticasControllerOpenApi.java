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

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.razzolim.food.domain.filter.VendaDiariaFilter;
import com.razzolim.food.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", 
                example = "1", dataType = "int"),
        @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
            example = "2019-12-01T00:00:00Z", dataType = "date-time"),
        @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
            example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro,
            
            @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                defaultValue = "+00:00")
            String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);
}
