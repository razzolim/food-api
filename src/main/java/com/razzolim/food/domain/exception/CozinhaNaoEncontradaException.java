package com.razzolim.food.domain.exception;

// a classe mãe já define o responseStatus, herda dela...
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String mensagem) {
	super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cidadeId) {
	this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
    }

}
