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
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public interface FotoStorageService {
    
    void armazenar(NovaFoto novaFoto);
    
    void remover(String nomeArquivo);
    
    InputStream recuperar(String nomeArquivo);
    
    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);
        
        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }
    
    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }
    
    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

}
