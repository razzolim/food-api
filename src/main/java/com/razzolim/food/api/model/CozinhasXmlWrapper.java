package com.razzolim.food.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.razzolim.food.domain.model.Cozinha;

import lombok.Data;
import lombok.NonNull;

/**
 * Função: empacotar uma lista de cozinhas.
 * 
 * @JacksonXmlRootElement mapeia o elemento raiz específico para serialização XML.
 * 
 * @author Renan
 *
 */
@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {
	
//	@JacksonXmlProperty(localName = "cozinha") funcionaria também
	@JsonProperty("cozinha")
	@JacksonXmlElementWrapper(useWrapping = false) // desabilitar o embrulho
	@NonNull // sem o nonNull o lombok nao gera o construtor que recebe cozinha.
	private List<Cozinha> cozinhas;

}
