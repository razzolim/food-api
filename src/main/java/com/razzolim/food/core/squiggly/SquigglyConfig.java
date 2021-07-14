/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Configuration
public class SquigglyConfig {
    
    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
	
	// "campos" -> substitui parametro default URL "fields"
	Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));
	
	// limitando os filtros apenas para esses recursos
	var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
	
	var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
	filterRegistration.setFilter(new SquigglyRequestFilter());
	filterRegistration.setOrder(1);
	filterRegistration.setUrlPatterns(urlPatterns);
	
	return filterRegistration;
    }

}
