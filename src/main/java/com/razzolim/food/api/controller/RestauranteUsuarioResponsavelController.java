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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.assembler.UsuarioModelAssembler;
import com.razzolim.food.api.model.UsuarioModel;
import com.razzolim.food.domain.model.Restaurante;
import com.razzolim.food.domain.service.CadastroRestauranteService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
	Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

	return usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
    }
}
