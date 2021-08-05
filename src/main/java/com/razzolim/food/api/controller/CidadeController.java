package com.razzolim.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

import com.razzolim.food.api.assembler.CidadeInputDisassembler;
import com.razzolim.food.api.assembler.CidadeModelAssembler;
import com.razzolim.food.api.exceptionhandler.Problem;
import com.razzolim.food.api.model.CidadeModel;
import com.razzolim.food.api.model.input.CidadeInput;
import com.razzolim.food.domain.exception.EstadoNaoEncontradoException;
import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.repository.CidadeRepository;
import com.razzolim.food.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;
    
    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler; 

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();
        
        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code= 400, message = "ID da cidade inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(
            @ApiParam(value = "ID de uma cidade", example = "1") 
            @PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        
        return cidadeModelAssembler.toModel(cidade);
    }

    @ApiOperation("Adiciona uma cidade")
    @ApiResponses({
        @ApiResponse(code= 201, message = "Cidade cadastrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade") 
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            
            cidade = cadastroCidade.salvar(cidade);
            
            return cidadeModelAssembler.toModel(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code= 200, message = "Cidade atualizada"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade com novos dados")
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


    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
        @ApiResponse(code= 204, message = "Cidade excluída"),
        @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @ApiParam(value = "ID de uma cidade", example = "1") 
            @PathVariable Long cidadeId) {
	cadastroCidade.excluir(cidadeId);
    }
    
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
