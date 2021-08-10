/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.springfox.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DefaultsProviderPlugin;
import springfox.documentation.spi.service.ResourceGroupingStrategy;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.SpringGroupingStrategy;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@SuppressWarnings("deprecation")
@Primary
@Component
public class DocumentationPluginsManagerAdapter extends DocumentationPluginsManager {
    
    @Autowired
    @Qualifier("resourceGroupingStrategyRegistry")
    private PluginRegistry<ResourceGroupingStrategy, DocumentationType> resourceGroupingStrategies;

    @Autowired
    @Qualifier("defaultsProviderPluginRegistry")
    private PluginRegistry<DefaultsProviderPlugin, DocumentationType> defaultsProviders;

    @Override
    public DocumentationContextBuilder createContextBuilder(DocumentationType documentationType,
            DefaultConfiguration defaultConfiguration) {
        return defaultsProviders.getPluginOrDefaultFor(documentationType, defaultConfiguration).create(documentationType)
                .withResourceGroupingStrategy(resourceGroupingStrategy(documentationType));
    }

    @Override
    public ResourceGroupingStrategy resourceGroupingStrategy(DocumentationType documentationType) {
        return resourceGroupingStrategies.getPluginOrDefaultFor(documentationType, new SpringGroupingStrategy());
    }

}
