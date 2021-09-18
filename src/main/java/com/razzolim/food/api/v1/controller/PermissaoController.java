package com.razzolim.food.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.v1.assembler.PermissaoModelAssembler;
import com.razzolim.food.api.v1.model.PermissaoModel;
import com.razzolim.food.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.razzolim.food.core.security.CheckSecurity;
import com.razzolim.food.domain.model.Permissao;
import com.razzolim.food.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoModel> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();

		// TODO refatorar depois. Precisa adicionar os links do HATEOAS
		return null; //permissaoModelAssembler.toCollectionModel(todasPermissoes);
	}

}