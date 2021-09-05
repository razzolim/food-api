package com.razzolim.food.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.v1.assembler.CozinhaInputDisassembler;
import com.razzolim.food.api.v1.assembler.CozinhaModelAssembler;
import com.razzolim.food.api.v1.model.CozinhaModel;
import com.razzolim.food.api.v1.model.input.CozinhaInput;
import com.razzolim.food.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.razzolim.food.domain.model.Cozinha;
import com.razzolim.food.domain.repository.CozinhaRepository;
import com.razzolim.food.domain.service.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourceAssembler;

	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable) {
		log.info("Consultando cozinhas com páginas de {} registros...", pageable.getPageSize());
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

		return pagedResourceAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cadastroCozinha.salvar(cozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

		return cozinhaModelAssembler.toModel(cozinhaAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinha.excluir(id);
	}

}
