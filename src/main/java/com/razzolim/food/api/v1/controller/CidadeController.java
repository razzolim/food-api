package com.razzolim.food.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.ResourceUriHelper;
import com.razzolim.food.api.v1.assembler.CidadeInputDisassembler;
import com.razzolim.food.api.v1.assembler.CidadeModelAssembler;
import com.razzolim.food.api.v1.model.CidadeModel;
import com.razzolim.food.api.v1.model.input.CidadeInput;
import com.razzolim.food.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.razzolim.food.core.security.CheckSecurity;
import com.razzolim.food.domain.exception.EstadoNaoEncontradoException;
import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.repository.CidadeRepository;
import com.razzolim.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;
    
    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler; 

    @CheckSecurity.Cidades.PodeConsultar
    @Deprecated // documentacao
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();
        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        return cidadeModelAssembler.toModel(cidade);
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            
            cidade = cadastroCidade.salvar(cidade);
            
            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
            
            ResourceUriHelper.addUriInResponseheader(cidadeModel.getId());
            
            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput) {
        
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
            
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
            
            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            
            return cidadeModelAssembler.toModel(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
    	cadastroCidade.excluir(cidadeId);
    }
    
    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/pageable")
    public Page<Cidade> listPageable(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
	
		try {
		    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		    return cadastroCidade.listPageable(pageable);
		} catch (Exception error) {
		    throw new NegocioException(error.getMessage(), error);
		}	
    }

}
