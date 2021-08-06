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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.razzolim.food.api.assembler.PedidoInputDisassembler;
import com.razzolim.food.api.assembler.PedidoModelAssembler;
import com.razzolim.food.api.assembler.PedidoResumoModelAssembler;
import com.razzolim.food.api.model.PedidoModel;
import com.razzolim.food.api.model.PedidoResumoModel;
import com.razzolim.food.api.model.input.PedidoInput;
import com.razzolim.food.core.data.PageableTranslator;
import com.razzolim.food.domain.exception.EntidadeNaoEncontradaException;
import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.filter.PedidoFilter;
import com.razzolim.food.domain.model.Pedido;
import com.razzolim.food.domain.model.Usuario;
import com.razzolim.food.domain.repository.PedidoRepository;
import com.razzolim.food.domain.service.EmissaoPedidoService;
import com.razzolim.food.infrastructure.repository.spec.PedidoSpecs;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    
    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;
    
    /*@GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
	List<Pedido> todosPedidos = pedidoRepository.findAll();
	List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	
	MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
	
	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
	
	if (StringUtils.isNotBlank(campos)) {
	    filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
	}
	
	pedidosWrapper.setFilters(filterProvider);
	
	return pedidosWrapper;
    }*/

    @ApiImplicitParams({
        @ApiImplicitParam(
                value = "Nomes das propriedades para filtrar na resposta, separados por vírgula.",
                name = "campos", paramType = "query", type = "string")
    })
    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
	
	pageable = traduzirPageable(pageable);
	
	Page<Pedido> pedidosPage = pedidoRepository.findAll(
		PedidoSpecs.usandoFiltro(filtro), pageable);
	
	List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
		.toCollectionModel(pedidosPage.getContent());
	
	return new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
    }

    @ApiImplicitParams({
        @ApiImplicitParam(
                value = "Nomes das propriedades para filtrar na resposta, separados por vírgula.",
                name = "campos", paramType = "query", type = "string")
    })
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
	Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
	return pedidoModelAssembler.toModel(pedido);
    }
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    private Pageable traduzirPageable(Pageable pageable) {
	var mapeamento = ImmutableMap.of(
		"codigo", "codigo",
		"restaurante.nome", "restaurante.nome",
		"nomeCliente", "cliente.nome",
		"valorTotal", "valorTotal");
	
	return PageableTranslator.translate(pageable, mapeamento);
    }
}
