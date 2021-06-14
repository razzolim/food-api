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

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
	this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
	boolean valido = true;
	
	if (value != null) { 
	    var valorDecimal = BigDecimal.valueOf(value.doubleValue());
	    var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
	    
	    var resto = valorDecimal.remainder(multiploDecimal);
	    valido = BigDecimal.ZERO.compareTo(resto) == 0;
	}
	
	return valido;
    }

}
