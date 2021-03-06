/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.razzolim.food.api.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Getter
@Setter
public class RestauranteModel {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;
    
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
    
    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;
    
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;
    
}
