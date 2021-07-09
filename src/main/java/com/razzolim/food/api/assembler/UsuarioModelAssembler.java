/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.model.UsuarioModel;
import com.razzolim.food.domain.model.Usuario;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class UsuarioModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel toModel(Usuario usuario) {
	return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
	return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
    }
}
