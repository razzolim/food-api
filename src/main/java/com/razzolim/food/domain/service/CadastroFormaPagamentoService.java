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
import com.razzolim.food.domain.exception.FormaPagamentoNaoEncontradaException;
import com.razzolim.food.domain.model.FormaPagamento;
import com.razzolim.food.domain.repository.FormaPagamentoRepository;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Service
public class CadastroFormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO 
        = "Forma de pagamento de código %d não pode ser removida, pois está em uso";
    
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    
    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }
    
    @Transactional
    public void excluir(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();
            
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
        
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_FORMA_PAGAMENTO_EM_USO, formaPagamentoId));
        }
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
            .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }   
}
