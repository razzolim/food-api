package com.razzolim.food.domain.exception;

// a classe mãe já define o responseStatus, herda dela...
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem) {
	super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long estadoId) {
	this(String.format("Não existe um cadastro de restaurante com código %d", estadoId));
    }

}
