/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.v1.model.CozinhaModel;
import com.razzolim.food.api.v1.model.PedidoResumoModel;
import com.razzolim.food.api.v1.openapi.model.CozinhasModelOpenApi;
import com.razzolim.food.api.v1.openapi.model.PageableModelOpenApi;
import com.razzolim.food.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.razzolim.food.api.v2.model.CidadeModelV2;
import com.razzolim.food.api.v2.model.CozinhaModelV2;
import com.razzolim.food.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.razzolim.food.api.v2.openapi.model.CozinhasModelV2OpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
    
    @Bean // para depreciar a documentação só remover o bean pra esse docket
    public Docket apiDocketV1() {
        var typeResolver = new TypeResolver();
        
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("V1")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.razzolim.food.api"))
                    .paths(PathSelectors.ant("/v1/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, CozinhaModel.class),
                        CozinhasModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoModel.class),
                        PedidosResumoModelOpenApi.class))
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfoV1())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"));
    }@Bean
    public Docket apiDocketV2() {
        var typeResolver = new TypeResolver();
        
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("V2")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.razzolim.food.api"))
                    .paths(PathSelectors.ant("/v2/**"))
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                	    typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
                	    CozinhasModelV2OpenApi.class))
            	.alternateTypeRules(AlternateTypeRules.newRule(
            	        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
            	        CidadesModelV2OpenApi.class))
            	.apiInfo(apiInfoV2())            	        
            	.tags(new Tag("Cidades", "Gerencia as cidades"),
                	        new Tag("Cozinhas", "Gerencia as cozinhas"));
    }
    
    private SecurityScheme securityScheme() {
    	return new OAuthBuilder()
    			.name("Food")
    			.grantTypes(grantTypes())
    			.scopes(scopes())
    			.build();
    }
    
    private SecurityContext securityContext() {
    	var securityReference = SecurityReference.builder()
    			.reference("Food")
    			.scopes(scopes().toArray(new AuthorizationScope[0]))
    			.build();
    	
    	return SecurityContext.builder()
    			.securityReferences(Arrays.asList(securityReference))
    			.forPaths(PathSelectors.any())
    			.build();
    }
    
    private List<GrantType> grantTypes() {
    	return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }
    
    private List<AuthorizationScope> scopes() {
    	return Arrays.asList(
    			new AuthorizationScope("READ", "Acesso de leitura"),
    			new AuthorizationScope("WRITE", "Acesso de escrita")
    		);
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
    
    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Food API (Depreciada)")
                .description("API aberta para clientes e restaurantes.<br>"
                		+ "<strong>Essa versão da API está depreciada e deixará de existir a partir de dd/MM/YYYY. "
                		+ "Use a versão mais atual da API.</strong>")
                .version("1")
                .contact(new Contact("Renan", "renan.azzolim", "azzolimrenan2@gmail.com"))
                .build();
    }
    
    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("Food API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
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
