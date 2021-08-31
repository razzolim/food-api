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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.controller.CozinhaController;
import com.razzolim.food.api.model.CozinhaModel;
import com.razzolim.food.domain.model.Cozinha;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    public CozinhaModelAssembler() {
    	super(CozinhaController.class, CozinhaModel.class);
    }
    
    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
    	CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
    	modelMapper.map(cozinha, cozinhaModel);
    	cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
    	return cozinhaModel;
    }
    
} 
