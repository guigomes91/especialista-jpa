package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConversorTest extends EntityManagerTest {
    @Test
    public void converter() {
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setNome("Carregador de Notebook Dell");
        produto.setAtivo(Boolean.TRUE);
        produto.setPreco(new BigDecimal(299));
        produto.setDescricao("Dell");

        entityManager.getTransaction().begin();

        entityManager.persist(produto);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertTrue(produtoVerificacao.getAtivo());
    }
}
