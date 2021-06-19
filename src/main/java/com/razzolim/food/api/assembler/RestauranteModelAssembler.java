/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.razzolim.food.api.model.CozinhaModel;
import com.razzolim.food.api.model.RestauranteModel;
import com.razzolim.food.domain.model.Restaurante;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Component
public class RestauranteModelAssembler {

    public RestauranteModel toModel(Restaurante restaurante) {
	CozinhaModel cozinhaModel = new CozinhaModel();
	cozinhaModel.setId(restaurante.getCozinha().getId());
	cozinhaModel.setNome(restaurante.getCozinha().getNome());
	
	RestauranteModel restauranteModel = new RestauranteModel();
	restauranteModel.setId(restaurante.getId());
	restauranteModel.setNome(restaurante.getNome());
	restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
	restauranteModel.setCozinha(cozinhaModel);
	return restauranteModel;
    }
    
    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
	return restaurantes.stream()
		.map(restaurante -> toModel(restaurante))
		.collect(Collectors.toList());
    }

}
