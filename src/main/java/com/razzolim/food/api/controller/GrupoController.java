/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.assembler.GrupoInputDisassembler;
import com.razzolim.food.api.assembler.GrupoModelAssembler;
import com.razzolim.food.api.model.GrupoModel;
import com.razzolim.food.api.model.input.GrupoInput;
import com.razzolim.food.domain.model.Grupo;
import com.razzolim.food.domain.repository.GrupoRepository;
import com.razzolim.food.domain.service.CadastroGrupoService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {
	List<Grupo> todosGrupos = grupoRepository.findAll();

	return grupoModelAssembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
	Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

	return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
	Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

	grupo = cadastroGrupo.salvar(grupo);

	return grupoModelAssembler.toModel(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
	    @RequestBody @Valid GrupoInput grupoInput) {
	Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);

	grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

	grupoAtual = cadastroGrupo.salvar(grupoAtual);

	return grupoModelAssembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
	cadastroGrupo.excluir(grupoId);
    }
}
