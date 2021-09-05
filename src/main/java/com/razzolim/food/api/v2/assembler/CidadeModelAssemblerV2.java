/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v2.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.v2.FoodLinksV2;
import com.razzolim.food.api.v2.controller.CidadeControllerV2;
import com.razzolim.food.api.v2.model.CidadeModelV2;
import com.razzolim.food.domain.model.Cidade;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoodLinksV2 foodLinks;

	public CidadeModelAssemblerV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}

	@Override
	public CidadeModelV2 toModel(Cidade cidade) {
		CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeModel);

		cidadeModel.add(foodLinks.linkToCidades("cidades"));

		return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(linkTo(CidadeControllerV2.class).withSelfRel());
	}

}
