package com.razzolim.food.domain.repository;

import java.util.List;

import com.razzolim.food.domain.model.Restaurante;

public interface RestauranteRepository {

	Restaurante salvar(Restaurante restaurante);
    List<Restaurante> listar();
    Restaurante buscar(Long id);
    void remover(Long id);

}
