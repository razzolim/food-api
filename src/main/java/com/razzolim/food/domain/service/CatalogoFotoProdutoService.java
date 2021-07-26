/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.model.FotoProduto;
import com.razzolim.food.domain.repository.ProdutoRepository;
import com.razzolim.food.domain.service.FotoStorageService.NovaFoto;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        
        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }
        
        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush(); // descarrega fila jpa
        
        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo).build();
        
        fotoStorageService.armazenar(novaFoto);

        return foto;
    }

}
