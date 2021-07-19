/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public final class PageableTranslator {
    
    private PageableTranslator() {
	// NA
    }
    
    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
	var orders = pageable.getSort().stream()
		.filter(order -> fieldsMapping.containsKey(order.getProperty()))
		.map(order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty())))
		.collect(Collectors.toList());
	
	return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

}
