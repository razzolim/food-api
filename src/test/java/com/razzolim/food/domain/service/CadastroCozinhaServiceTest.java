package com.razzolim.food.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.razzolim.food.domain.exception.CozinhaNaoEncontradaException;
import com.razzolim.food.domain.exception.EntidadeEmUsoException;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.repository.CozinhaRepository;

@ExtendWith(MockitoExtension.class)
class CadastroCozinhaServiceTest {
	
	@Mock
	private CozinhaRepository cozinhaRepository;
	
	@InjectMocks
	private CadastroCozinhaService cozinhaService;
	
	private Cozinha cozinha;
	
	@BeforeEach
	void setup() {
		cozinha = new Cozinha();
	}
	
	@Test
	@DisplayName("When cozinha is OK should save")
	void whenCozinhaIsOKShouldSave() {
		when(cozinhaRepository.save(Mockito.any(Cozinha.class))).thenReturn(cozinha);
		
		Cozinha cozinhaSalva = cozinhaService.salvar(new Cozinha());
		
		assertThat(cozinhaSalva).isNotNull();		
	}
	
	@Test
	@DisplayName("When cozinha is OK should exclude")
	void whenCozinhaIsOKShouldExclude() {
		cozinhaService.excluir(1L);
		verify(cozinhaRepository).deleteById(anyLong());
	}
	
	@Test
	@DisplayName("When excluding cozinha should throw CozinhaNaoEncontradaException")
	void whenExcludingCozinhaAndDoesNotExistsShouldThrowException() {
		doThrow(EmptyResultDataAccessException.class)
			.when(cozinhaRepository).deleteById(anyLong());

		Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cozinhaService.excluir(1L);
		});
	}
	
	@Test
	@DisplayName("When excluding cozinha in use should throw EntidadeEmUsoException")
	void whenExcludingCozinhaInUseShouldThrowException() {
		doThrow(DataIntegrityViolationException.class)
			.when(cozinhaRepository).deleteById(anyLong());

		Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			cozinhaService.excluir(1L);
		});
	}
	
	@Test
	@DisplayName("When cozinha exists should return valid cozinha")
	void shouldReturnValidCozinha() {
		cozinha.setId(1L);
		
		when(cozinhaRepository.findById(anyLong()))
			.thenReturn(Optional.of(cozinha));
		
		Cozinha cozinhaRetornada = cozinhaService.buscarOuFalhar(anyLong());
		
		assertThat(cozinhaRetornada).isNotNull();
		assertThat(cozinhaRetornada.getId()).isEqualTo(cozinha.getId());
	}
	
	@Test
	@DisplayName("When buscar cozinha should throw CozinhaNaoEncontradaException")
	void shouldThrowExceptionWhenCozinhaDoesNotExists() {
		when(cozinhaRepository.findById(anyLong()))
			.thenReturn(Optional.empty());
		
		Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cozinhaService.buscarOuFalhar(1L);
		});
	}
}
