package com.razzolim.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.razzolim.food.domain.exception.CozinhaNaoEncontradaException;
import com.razzolim.food.domain.exception.EntidadeEmUsoException;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
	return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void excluir(Long id) {
	try {
	    cozinhaRepository.deleteById(id);
	    
	    cozinhaRepository.flush();
	    
	} catch (EmptyResultDataAccessException error) {
	    throw new CozinhaNaoEncontradaException(id);
	} catch (DataIntegrityViolationException error) {
	    throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
	}
    }

    public Cozinha buscarOuFalhar(Long id) {
	return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }
}
