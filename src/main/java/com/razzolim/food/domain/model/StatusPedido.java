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

import java.util.Arrays;
import java.util.List;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Confirmado", CRIADO);
    
    private String descricao;
    private List<StatusPedido> statusAnteriores;
    
    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
	this.descricao = descricao;
	this.statusAnteriores = Arrays.asList(statusAnteriores);
    }
    
    public String getDescricao() {
	return this.descricao;
    }
    
    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
	return !novoStatus.statusAnteriores.contains(this);
    }
}
