/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v1.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan Azzolim
 * 
 * Classe criada especificamente com objeto de documentação para open api.
 *
 * @since 
 * 
 */
@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {
    
    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private int page;
    
    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página)")
    private int size;
    
    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
    private List<String> sort;

}
