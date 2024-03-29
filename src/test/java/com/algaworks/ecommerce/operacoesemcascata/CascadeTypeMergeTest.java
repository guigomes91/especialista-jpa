package com.algaworks.ecommerce.operacoesemcascata;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CascadeTypeMergeTest extends EntityManagerTest {

    @Test
    public void persistirProdutoComCategoria() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Teste 1");
        produto.setDataCriacao(LocalDateTime.now());
        produto.setTags(List.of("TESTE TAG"));

        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setId(2);
        categoriaFilha.setNome("Livro 2");
        produto.setCategorias(List.of(categoriaFilha));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Livro 2", produtoVerificacao.getCategorias().get(0).getNome());
    }

}
