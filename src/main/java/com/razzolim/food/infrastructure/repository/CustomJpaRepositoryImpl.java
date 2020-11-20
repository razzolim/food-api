package com.razzolim.food.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.razzolim.food.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> 
		implements CustomJpaRepository<T, ID> {
	
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		
		StringBuilder jpql = new StringBuilder();
		
		jpql.append("from ");
		jpql.append(getDomainClass().getName());
		
		T entity = manager.createQuery(jpql.toString(), getDomainClass())
			.setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);
	}

}
