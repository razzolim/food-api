package com.razzolim.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razzolim.food.domain.exception.RestauranteNaoEncontradoException;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.model.FormaPagamento;
import com.razzolim.food.domain.model.Restaurante;
import com.razzolim.food.domain.model.Usuario;
import com.razzolim.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CadastroFormaPagamentoService pagamentoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
	Long cozinhaId = restaurante.getCozinha().getId();
	Long cidadeId = restaurante.getEndereco().getCidade().getId();

	Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

	restaurante.setCozinha(cozinha);
	restaurante.getEndereco().setCidade(cidade);

	return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	restauranteAtual.inativar();
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
	Restaurante restaurante = buscarOuFalhar(restauranteId);
	FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagamentoId);
	restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
	Restaurante restaurante = buscarOuFalhar(restauranteId);
	FormaPagamento formaPagamento = pagamentoService.buscarOuFalhar(formaPagamentoId);
	restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long restauranteId) {
	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	restauranteAtual.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
	Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	restauranteAtual.fechar();
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	Restaurante restaurante = buscarOuFalhar(restauranteId);
	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

	restaurante.removerResponsavel(usuario);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
	Restaurante restaurante = buscarOuFalhar(restauranteId);
	Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);

	restaurante.adicionarResponsavel(usuario);
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
	return restauranteRepository.findById(restauranteId)
		.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

}
