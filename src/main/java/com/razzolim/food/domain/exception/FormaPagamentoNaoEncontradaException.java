/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.exception;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public FormaPagamentoNaoEncontradaException(Long estadoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d", estadoId));
    }
    
}
