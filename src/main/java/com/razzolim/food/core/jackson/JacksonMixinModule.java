/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.razzolim.food.api.model.mixin.CidadeMixin;
import com.razzolim.food.api.model.mixin.CozinhaMixin;
import com.razzolim.food.api.model.mixin.RestauranteMixin;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.model.Restaurante;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
	setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
	setMixInAnnotation(Cidade.class, CidadeMixin.class);
	setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
