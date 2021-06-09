package com.razzolim.food.domain.exception;

// a classe mãe já define o responseStatus, herda dela...
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem) {
	super(mensagem);
    }

    public EstadoNaoEncontradoException(Long estadoId) {
	this(String.format("Não existe um cadastro de estado com código %d", estadoId));
    }

}
