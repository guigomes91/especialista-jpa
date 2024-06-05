package com.algaworks.repository;

import com.algaworks.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Produtos {

    @PersistenceContext
    private EntityManager entityManager;

    public Produto buscar(Integer id) {
        return entityManager.find(Produto.class, id);
    }

    public Produto salvar(Produto produto) {
        return entityManager.merge(produto);
    }

    public List<Produto> listar() {
        return entityManager
                .createQuery("select p from Produto p", Produto.class)
                .getResultList();
    }
}
