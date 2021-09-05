/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.razzolim.food.api.v1.model.GrupoModel;
import com.razzolim.food.domain.model.Grupo;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }
    
    public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }   
}
