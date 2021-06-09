package com.razzolim.food.domain.exception;

// a classe mãe já define o responseStatus, herda dela...
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String mensagem) {
	super(mensagem);
    }

    public CidadeNaoEncontradaException(Long restauranteId) {
	this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }

}
