package com.razzolim.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.razzolim.food.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete",
	descricaoField = "nome", descricaoObrigatoria = "Frete grátis")
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
//	@NotBlank(message = "Nome é obrigatório.") repetitivo e burocrático
//	@NotBlank
	@Column(nullable = false)
	private String nome;

//	@PositiveOrZero(message = "{TaxaFrete.invalida}")
//	@TaxaFrete
//	@Multiplo(numero = 5)
//	@NotNull
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	/**
	 * @ManyToOne muitos restaurantes possuem uma cozinha
	 */
//	@JsonIgnore
//	@JsonIgnoreProperties("hibernateLazyInitializer") /* ignora propriedades que estão dentro da instancia de cozinha */
//	@JsonIgnoreProperties(value = "nome", allowGetters = true) // allowGetters -> não vai ignorar na hora de serializar o json
//	@Valid // por padrão o bean validation não valida por cascata... cozinha.id resolve com @Valid
//	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
//	@NotNull // como está usando DTO, as validações podem sair daqui pois já estão lá
	@ManyToOne //(fetch = FetchType.LAZY) /* todas as anotações q terminam com ToOne utilizam default a estratégia eager loading */
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	/**
	 * @Embedeed essa propriedade se torna de um tipo incorporável, complementando
	 *           anotação na classe do endereço
	 */
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	private Boolean aberto = Boolean.FALSE;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
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
	@ManyToMany//(fetch = FetchType.EAGER) /* todas as anotações q terminam com ToMany utilizam default a estratégia lazy loading */
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	public void abrir() {
	    setAberto(true);
	}

	public void fechar() {
	    setAberto(false);
	}
	
	public void ativar() {
	    setAtivo(true);
	}
	
	public void inativar() {
	    setAtivo(false);
	}
	
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().add(formaPagamento);
	}

}
