/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.v1.FoodLinks;
import com.razzolim.food.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.razzolim.food.core.security.CheckSecurity;
import com.razzolim.food.domain.filter.VendaDiariaFilter;
import com.razzolim.food.domain.model.dto.VendaDiaria;
import com.razzolim.food.domain.service.VendaQueryService;
import com.razzolim.food.domain.service.VendaReportService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

	@Autowired
	private VendaQueryService vendaQueryService;

	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private FoodLinks foodLinks;
	
	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
		var estatisticasModel = new EstatisticasModel();
		
		// TODO refatorar incluindo links HATEOAS
		// estatisticasModel.add(foodLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		
		return estatisticasModel;
	}

	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}

	@CheckSecurity.Estatisticas.PodeConsultar
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
	}
	
	
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}

}
