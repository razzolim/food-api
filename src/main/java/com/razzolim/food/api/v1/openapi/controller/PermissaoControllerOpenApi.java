package com.razzolim.food.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.razzolim.food.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões")
	CollectionModel<PermissaoModel> listar();
	
}
