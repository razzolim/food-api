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

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public void calcularPrecoTotal() {
	BigDecimal precoUnitario = this.getPrecoUnitario();
	Integer quantidade = this.getQuantidade();

	if (precoUnitario == null) {
	    precoUnitario = BigDecimal.ZERO;
	}

	if (quantidade == null) {
	    quantidade = 0;
	}

	this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
    }
}
