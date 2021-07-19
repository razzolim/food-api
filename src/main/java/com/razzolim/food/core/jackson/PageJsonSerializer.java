/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers)
	    throws IOException {
	
	gen.writeStartObject();
	
	gen.writeObjectField("content", page.getContent());
	gen.writeNumberField("size", page.getSize());
	gen.writeNumberField("totalElements", page.getTotalElements());
	gen.writeNumberField("totalPages", page.getTotalPages());
	gen.writeNumberField("number", page.getNumber());
	
	gen.writeEndObject();
    }

}
