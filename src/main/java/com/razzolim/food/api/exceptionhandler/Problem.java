/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

    /* padrão RFC 7807 */
    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://food.com.br/dados-invalidos", position = 2) 
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 5) 
    private String title;
    
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente", position = 10)
    private String detail;
    
    /* details/extensão/complemento */
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente", position = 15)
    private String userMessage;
    
    @ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 20) 
    private OffsetDateTime timestamp;
    
    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 25) 
    private List<Object> objects;
    
    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "preço") 
	private String name;
        
        @ApiModelProperty(example = "O preço é obrigatório") 
	private String userMessage;
	
    }

}
