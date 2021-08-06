/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Setter
@Getter
public class PedidoFilter {
    
    private Long clienteId;

    @ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
    private Long restauranteId;

    @ApiModelProperty(example = "2019-10-30T00:00:00Z",
            value = "Data/hora de criação inicial para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @ApiModelProperty(example = "2019-11-01T10:00:00Z",
            value = "Data/hora de criação final para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

}
