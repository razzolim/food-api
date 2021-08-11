package com.razzolim.food.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.razzolim.food.api.assembler.CidadeInputDisassembler;
import com.razzolim.food.api.assembler.CidadeModelAssembler;
import com.razzolim.food.api.model.CidadeModel;
import com.razzolim.food.api.model.input.CidadeInput;
import com.razzolim.food.api.openapi.controller.CidadeControllerOpenApi;
import com.razzolim.food.domain.exception.EstadoNaoEncontradoException;
import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.model.Cidade;
import com.razzolim.food.domain.repository.CidadeRepository;
import com.razzolim.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;
    
    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler; 

    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();
        
        return cidadeModelAssembler.toCollectionModel(todasCidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
        
        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
        
//        cidadeModel.add(new Link("http://localhost:8080/cidades/1"));
//        cidadeModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//                .slash(cidadeModel.getId()).withSelfRel());
        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeModel.getId())).withSelfRel());
        
//        cidadeModel.add(new Link("http://localhost:8080/cidades", "cidades"));
//        cidadeModel.add(linkTo(CidadeController.class).withRel("cidades"));
        cidadeModel.add(linkTo(methodOn(CidadeController.class)
                .listar()).withRel("cidades"));
        
//        cidadeModel.getEstado().add(new Link("http://localhost:8080/estados/1", "estados"));
//        cidadeModel.getEstado().add(linkTo(EstadoController.class)
//                .slash(cidadeModel.getEstado().getId()).withSelfRel());
        cidadeModel.add(linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId())).withSelfRel());
        
        return cidadeModel;
    }

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


    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
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
