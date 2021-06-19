package com.razzolim.food.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.api.model.CozinhaModel;
import com.razzolim.food.api.model.RestauranteModel;
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

    @GetMapping
    public List<RestauranteModel> listar() {
	return toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
	Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid Restaurante restaurante) {
	try {
	    return toModel(cadastroRestaurante.salvar(restaurante));
	} catch (CozinhaNaoEncontradaException error) {
	    throw new NegocioException(error.getMessage());
	}
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId,
	    @RequestBody @Valid Restaurante restaurante) {
	Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

	BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
		"dataCadastro", "produtos");

	try {
	    return toModel(cadastroRestaurante.salvar(restauranteAtual));
	} catch (CozinhaNaoEncontradaException error) {
	    throw new NegocioException(error.getMessage());
	}
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

    private RestauranteModel toModel(Restaurante restaurante) {
	CozinhaModel cozinhaModel = new CozinhaModel();
	cozinhaModel.setId(restaurante.getCozinha().getId());
	cozinhaModel.setNome(restaurante.getCozinha().getNome());
	
	RestauranteModel restauranteModel = new RestauranteModel();
	restauranteModel.setId(restaurante.getId());
	restauranteModel.setNome(restaurante.getNome());
	restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
	restauranteModel.setCozinha(cozinhaModel);
	return restauranteModel;
    }
    
    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
	return restaurantes.stream()
		.map(restaurante -> toModel(restaurante))
		.collect(Collectors.toList());
    }
}
