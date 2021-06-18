/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
public class ValorZeroIncluiDescricaoValidator
	implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
	this.valorField = constraint.valorField();
	this.descricaoField = constraint.descricaoField();
	this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
	boolean valido = true;

	try {
	    BigDecimal valor = (BigDecimal) BeanUtils
		    .getPropertyDescriptor(objetoValidacao.getClass(), valorField).getReadMethod()
		    .invoke(objetoValidacao);

	    String descricao = (String) BeanUtils
		    .getPropertyDescriptor(objetoValidacao.getClass(), descricaoField)
		    .getReadMethod().invoke(objetoValidacao);
	    
	    if ((valor != null && BigDecimal.ZERO.compareTo(valor) == 0) && descricao != null) {
		valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
	    }
	    
	} catch (Exception error) {
	    throw new ValidationException(error);
	}

	return valido;
    }

}
