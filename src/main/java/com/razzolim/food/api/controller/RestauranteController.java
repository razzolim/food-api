package com.razzolim.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.razzolim.food.api.assembler.RestauranteInputDisassembler;
import com.razzolim.food.api.assembler.RestauranteModelAssembler;
import com.razzolim.food.api.model.RestauranteModel;
import com.razzolim.food.api.model.input.RestauranteInput;
import com.razzolim.food.domain.exception.CidadeNaoEncontradaException;
import com.razzolim.food.domain.exception.CozinhaNaoEncontradaException;
import com.razzolim.food.domain.exception.NegocioException;
import com.razzolim.food.domain.model.Restaurante;
import com.razzolim.food.domain.repository.RestauranteRepository;
import com.razzolim.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    /*
        @Autowired
        private SmartValidator validator;
    */
    
    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;
    
    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler; 

    @GetMapping
    public List<RestauranteModel> listar() {
	return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
	Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
	try {
	    Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
	    return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
	} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException error) {
	    throw new NegocioException(error.getMessage());
	}
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
	    @RequestBody @Valid RestauranteInput restauranteInput) {

	Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
	
//	Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
	
//	BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
//		"dataCadastro", "produtos");

	restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
	
	try {
	    return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
	} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException error) {
	    throw new NegocioException(error.getMessage());
	}
    }
    
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
	cadastroRestaurante.ativar(restauranteId);
    }
    
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
	cadastroRestaurante.inativar(restauranteId);
    }
    
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestaurante.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestaurante.fechar(restauranteId);
    }

    /*
    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
	    @RequestBody Map<String, Object> campos, HttpServletRequest request) {
	Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

	merge(campos, restauranteAtual, request);
	validate(restauranteAtual, "restaurente");

	return atualizar(restauranteId, restauranteAtual);
    }

    private void validate(Restaurante restaurante, String objectName) {
	BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante,
		objectName);
	validator.validate(restaurante, bindingResult);
	
	if (bindingResult.hasErrors()) {
 	    throw new ValidacaoException(bindingResult);
	}
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
	    HttpServletRequest request) {
	ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

	try {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

	    Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem,
		    Restaurante.class);

	    dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
		Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
		field.setAccessible(true);
		Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
		ReflectionUtils.setField(field, restauranteDestino, novoValor);
	    });
	} catch (IllegalArgumentException error) {
	    Throwable rootCause = ExceptionUtils.getRootCause(error);
	    throw new HttpMessageNotReadableException(error.getMessage(), rootCause,
		    serverHttpRequest);
	}
    }
    */
}
