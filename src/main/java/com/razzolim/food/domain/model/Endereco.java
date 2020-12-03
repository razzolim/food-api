package com.razzolim.food.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @Embeddable faz com que a classe tenha capacidade de ser incorporada em outra
 *             entidade. A classe é parte de uma outra entitdade, e não uma
 *             entidade em si. Os atributos desta classe aparecerão na entidade
 *             que está incorporando essa classe.
 * 
 * @author Renan
 *
 */
@Data
@Embeddable
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;

	@Column(name = "endereco_logradouro")
	private String logradouro;

	@Column(name = "endereco_numero")
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@Column(name = "endereco_bairro")
	private String bairro;

	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;

}
