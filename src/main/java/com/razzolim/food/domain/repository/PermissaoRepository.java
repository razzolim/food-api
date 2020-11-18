package com.razzolim.food.domain.repository;

import java.util.List;

import com.razzolim.food.domain.model.Permissao;

public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);

}
