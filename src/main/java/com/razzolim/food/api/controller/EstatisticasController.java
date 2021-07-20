/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.domain.filter.VendaDiariaFilter;
import com.razzolim.food.domain.model.dto.VendaDiaria;
import com.razzolim.food.domain.service.VendaQueryService;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {
    
    @Autowired
    private VendaQueryService vendaQueryService;
    
    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, 
	    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
	return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }

}
