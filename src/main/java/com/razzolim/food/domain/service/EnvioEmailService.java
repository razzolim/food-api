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

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public interface EnvioEmailService {
    
    void enviar(Mensagem mensagem);
    
    @Getter
    @Builder
    class Mensagem {
        
        @Singular
        private Set<String> destinatarios;
        
        @NonNull
        private String assunto;
        
        @NonNull
        private String corpo;
        
        @Singular("variavel")
        private Map<String, Object> variaveis;
        
    }

}
