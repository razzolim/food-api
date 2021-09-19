/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.v1.assembler.PermissaoModelAssembler;
import com.razzolim.food.api.v1.model.PermissaoModel;
import com.razzolim.food.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.razzolim.food.core.security.CheckSecurity;
import com.razzolim.food.core.security.FoodSecurity;
import com.razzolim.food.domain.model.Grupo;
import com.razzolim.food.domain.service.CadastroGrupoService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
    
    @Autowired
    private FoodSecurity foodSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

		return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
	}

// TODO refatorar para esse método após incluir links HATEOAS	
//	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
//	@Override
//	@GetMapping
//	public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
//	    Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
//	    
//	    CollectionModel<PermissaoModel> permissoesModel 
//	        = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
//	            .removeLinks();
//	    
//	    permissoesModel.add(algaLinks.linkToGrupoPermissoes(grupoId));
//	    
//	    if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
//	        permissoesModel.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));
//	    
//	        permissoesModel.getContent().forEach(permissaoModel -> {
//	            permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
//	                    grupoId, permissaoModel.getId(), "desassociar"));
//	        });
//	    }
//	    
//	    return permissoesModel;
//	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
	}

}
