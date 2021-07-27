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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.razzolim.food.core.storage.StorageProperties.TipoStorage;
import com.razzolim.food.domain.service.FotoStorageService;
import com.razzolim.food.infrastructure.service.storage.LocalFotoStorageService;
import com.razzolim.food.infrastructure.service.storage.S3FotoStorageService;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }
    
    @Bean
    public FotoStorageService fotoStorageService() {
        if (TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService(); 
        }
    }

}
