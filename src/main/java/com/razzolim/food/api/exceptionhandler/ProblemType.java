/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.exceptionhandler;

import lombok.Getter;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Getter
public enum ProblemType {

    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos."),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível."),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio."); 

    private String title;

    private String uri;

    private ProblemType(String path, String title) {
	this.uri = "https://apifood.com.br" + path;
	this.title = title;
    }

}
