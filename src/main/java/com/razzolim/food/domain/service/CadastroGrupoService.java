/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.exception.EntidadeEmUsoException;
import com.razzolim.food.domain.exception.GrupoNaoEncontradoException;
import com.razzolim.food.domain.model.Grupo;
import com.razzolim.food.domain.repository.GrupoRepository;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(Grupo grupo) {
	return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long grupoId) {
	try {
	    grupoRepository.deleteById(grupoId);
	    grupoRepository.flush();

	} catch (EmptyResultDataAccessException e) {
	    throw new GrupoNaoEncontradoException(grupoId);

	} catch (DataIntegrityViolationException e) {
	    throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
	}
    }

    public Grupo buscarOuFalhar(Long grupoId) {
	return grupoRepository.findById(grupoId)
		.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }
}
