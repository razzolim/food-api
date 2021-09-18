package com.razzolim.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.razzolim.food.domain.model.Restaurante;

@Repository
public interface RestauranteRepository
		extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
			JpaSpecificationExecutor<Restaurante> {
	
	/**
	 * sem o join fetch em formasPagamento a query nao resolveria o N+1
	 */
	@Query("from Restaurante as r join fetch r.cozinha")
	List<Restaurante> findAll();

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	/**
	 * 3 formas: 1. findByNome 2. @Query()... 3. META-INF/orm.xml
	 * 
	 * @param nome
	 * @param cozinhaId
	 * @return
	 */
//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

	Optional<Restaurante> findFirstRestauranteByNome(String nome);

	List<Restaurante> findTop2ByNomeContaining(String nome);

	int countByCozinhaId(Long cozinhaId);
	
	boolean existsResponsavel(Long restauranteId, Long usuarioId);

}
