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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.model.input.RestauranteInput;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.model.Restaurante;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Component
public class RestauranteInputDisassembler {
    
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }
    
    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
	// evitar problema JPA entender que está alterando o ID da cozinha
	// qnd na vdd está alterando a cozinha do restaurante
	restaurante.setCozinha(new Cozinha());
	modelMapper.map(restauranteInput, restaurante);
    }
    
}
