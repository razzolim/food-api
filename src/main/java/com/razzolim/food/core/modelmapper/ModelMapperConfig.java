/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razzolim.food.api.v1.model.EnderecoModel;
import com.razzolim.food.api.v1.model.input.ItemPedidoInput;
import com.razzolim.food.api.v2.model.input.CidadeInputV2;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.model.Endereco;
import com.razzolim.food.domain.model.ItemPedido;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
	var modelMapper = new ModelMapper();

	modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
	    .addMappings(mapper -> mapper.skip(Cidade::setId)); 
	
	// utilizado para customizar o mapeamento das propriedades
//	modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//		.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
	
	// config para exibir nome do estado no campo CidadeResumoModel.estado e não o .toString()
	var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
	enderecoToEnderecoModelTypeMap.<String>addMapping(
		enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
		(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
	
	modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
	
	return modelMapper;
    }
    

}
