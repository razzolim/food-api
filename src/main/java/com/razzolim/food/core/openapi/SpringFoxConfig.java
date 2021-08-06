/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.model.CozinhaModel;
import com.razzolim.food.api.openapi.model.CozinhasModelOpenApi;
import com.razzolim.food.api.openapi.model.PageableModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {
    
    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();
        
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.razzolim.food.api"))
//                    .paths(PathSelectors.ant("/restaurantes/*"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                        .name("campos")
//                        .description("Nome das propriedades para filtrar na resposta, separados por vírgula")
//                        .parameterType("query")
//                        .modelRef(new ModelRef("string"))
//                        .build()
//                        ))
                .ignoredParameterTypes(ServletWebRequest.class)
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"))
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"))
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"))
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"))
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"))
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usu�rios"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"));
    }
    
    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Erro interno do servidor")
                .responseModel(new ModelRef("Problema"))
                .build(),
                new ResponseMessageBuilder()
                .code(HttpStatus.NOT_ACCEPTABLE.value())
                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .build()
           );
    }
    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Requisição inválida (erro do cliente)")
                    .responseModel(new ModelRef("Problema"))
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erro interno no servidor")
                    .responseModel(new ModelRef("Problema"))
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_ACCEPTABLE.value())
                    .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                    .message("Requisição recusada porque o corpo está em um formato não suportado")
                    .build()
            );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Requisição inválida (erro do cliente)")
                    .responseModel(new ModelRef("Problema"))
                    .build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Erro interno no servidor")
                    .responseModel(new ModelRef("Problema"))
                    .build()
            );
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("Renan", "renan.azzolim", "azzolimrenan2@gmail.com"))
                .build();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
