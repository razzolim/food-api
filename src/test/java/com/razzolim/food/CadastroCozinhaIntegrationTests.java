package com.razzolim.food;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.razzolim.food.domain.exception.EntidadeEmUsoException;
import com.razzolim.food.domain.exception.EntidadeNaoEncontradaException;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {
    
    @Autowired
    private CadastroCozinhaService cadastroCozinha;
    
    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
	// cenário
	Cozinha novaCozinha = new Cozinha();
	novaCozinha.setNome("Chinesa");
	
	// ação
	cadastroCozinha.salvar(novaCozinha);
	
	// validação
	assertThat(novaCozinha).isNotNull();
	assertThat(novaCozinha.getId()).isNotNull();
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void deveFalhar_QuandoSalvarCozinhaSemNome() {	
	Cozinha novaCozinha = new Cozinha();
	novaCozinha = cadastroCozinha.salvar(novaCozinha);
    }
    
    @Test(expected = EntidadeEmUsoException.class)
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
	cadastroCozinha.excluir(1L);
    }
    
    @Test(expected = EntidadeNaoEncontradaException.class)
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
	cadastroCozinha.excluir(100L);
    }

}
