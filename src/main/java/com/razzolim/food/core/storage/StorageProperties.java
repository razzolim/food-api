/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Getter
@Setter
@Component
@ConfigurationProperties("food.storage")
public class StorageProperties {
    
    private TipoStorage tipo = TipoStorage.LOCAL;
    private Local local = new Local();
    private S3 s3 = new S3();
    
    public enum TipoStorage {
        LOCAL, S3;
    }
    
    @Getter
    @Setter
    public class Local {
        
        private String diretorioFotos;
        
    }

    @Getter
    @Setter
    public class S3 {
        
        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;
        
    }

}
