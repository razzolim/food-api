package com.razzolim.food.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.razzolim.food.Groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Documentação bean validations
	 * https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints
	 */
//	@NotNull
//	@NotEmpty
	@NotBlank
	@Column(nullable = false)
	private String nome;

	@PositiveOrZero
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	/**
	 * @ManyToOne muitos restaurantes possuem uma cozinha
	 */
//	@JsonIgnore
//	@JsonIgnoreProperties("hibernateLazyInitializer") /* ignora propriedades que estão dentro da instancia de cozinha */
	@Valid // por padrão o bean validation não valida por cascata... cozinha.id resolve com @Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull 
	@ManyToOne //(fetch = FetchType.LAZY) /* todas as anotações q terminam com ToOne utilizam default a estratégia eager loading */
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	/**
	 * @Embedeed essa propriedade se torna de um tipo incorporável, complementando
	 *           anotação na classe do endereço
	 */
	@JsonIgnore
	@Embedded
	private Endereco endereco;

	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante") /* todas as anotações q terminam com ToMany utilizam default a estratégia lazy loading */
	private List<Produto> produtos = new ArrayList<>();  
	
	/**
	 * @JoinColumns define o nome da coluna do atributo dessa classe na tabela
	 *              composta
	 *              <p>
	 * 
	 * @InverseJoinColumns define o nome da coluna na tabela composta do atributo da
	 *                     classe que se relaciona com essa
	 */
	@JsonIgnore
	@ManyToMany//(fetch = FetchType.EAGER) /* todas as anotações q terminam com ToMany utilizam default a estratégia lazy loading */
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
