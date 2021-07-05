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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.exception.ProdutoNaoEncontradoException;
import com.razzolim.food.domain.model.Produto;
import com.razzolim.food.domain.repository.ProdutoRepository;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
	return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
	return produtoRepository.findById(restauranteId, produtoId)
		.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}
