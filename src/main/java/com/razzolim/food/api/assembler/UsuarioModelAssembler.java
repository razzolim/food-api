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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.FoodLinks;
import com.razzolim.food.api.controller.UsuarioController;
import com.razzolim.food.api.model.UsuarioModel;
import com.razzolim.food.domain.model.Usuario;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Component
public class UsuarioModelAssembler 
        extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(foodLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(foodLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
        return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(UsuarioController.class).withSelfRel());
    }   
}  
