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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.controller.CidadeController;
import com.razzolim.food.api.controller.EstadoController;
import com.razzolim.food.api.model.CidadeModel;
import com.razzolim.food.domain.model.Cidade;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
    private ModelMapper modelMapper;

    public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

    @Override
    public CidadeModel toModel(Cidade cidade) {
    	CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
    	modelMapper.map(cidade, cidadeModel);
    	
        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .listar()).withRel("cidades"));
        
        cidadeModel.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId())).withSelfRel());
        
        return cidadeModel;
    }
    
    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
    	return super.toCollectionModel(entities).add(linkTo(CidadeController.class).withSelfRel());
    }
    
}
