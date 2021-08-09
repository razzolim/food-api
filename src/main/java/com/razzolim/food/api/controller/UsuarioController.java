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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.assembler.UsuarioInputDisassembler;
import com.razzolim.food.api.assembler.UsuarioModelAssembler;
import com.razzolim.food.api.model.UsuarioModel;
import com.razzolim.food.api.model.input.SenhaInput;
import com.razzolim.food.api.model.input.UsuarioComSenhaInput;
import com.razzolim.food.api.model.input.UsuarioInput;
import com.razzolim.food.api.openapi.controller.UsuarioControllerOpenApi;
import com.razzolim.food.domain.model.Usuario;
import com.razzolim.food.domain.repository.UsuarioRepository;
import com.razzolim.food.domain.service.CadastroUsuarioService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
	List<Usuario> todasUsuarios = usuarioRepository.findAll();

	return usuarioModelAssembler.toCollectionModel(todasUsuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

	return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
	Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
	usuario = cadastroUsuario.salvar(usuario);

	return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId,
	    @RequestBody @Valid UsuarioInput usuarioInput) {
	Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
	usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
	usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

	return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
	cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
