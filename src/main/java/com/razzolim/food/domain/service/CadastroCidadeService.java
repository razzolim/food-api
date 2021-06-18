package com.razzolim.food.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.razzolim.food.domain.exception.CidadeNaoEncontradaException;
import com.razzolim.food.domain.exception.EntidadeEmUsoException;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.model.Estado;
import com.razzolim.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Transactional
    public Cidade salvar(Cidade cidade) {
	Long estadoId = cidade.getEstado().getId();

	Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

	cidade.setEstado(estado);

	return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
	try {
	    cidadeRepository.deleteById(cidadeId);

	} catch (EmptyResultDataAccessException e) {
	    throw new CidadeNaoEncontradaException(cidadeId);

	} catch (DataIntegrityViolationException e) {
	    throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
	}
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
	return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

}
