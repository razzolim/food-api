package com.razzolim.food.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razzolim.food.domain.model.Restaurante;
import com.razzolim.food.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/testes")
public class TesteController {
	
	@Autowired
	private RestauranteRepository repo;
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return repo.buscarPrimeiro();
	}

}
