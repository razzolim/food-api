/*
 * COPYRIGHT RENAN AZZOLIM & MAICON FANG 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim and Maicon Fang.
 */
package com.razzolim.food.domain.model;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado"),
    ENTREGUE("Entregue"),
    CANCELADO("Confirmado");
    
    private String descricao;
    
    StatusPedido(String descricao) {
	this.descricao = descricao;
    }
    
    public String getDescricao() {
	return this.descricao;
    }
}
