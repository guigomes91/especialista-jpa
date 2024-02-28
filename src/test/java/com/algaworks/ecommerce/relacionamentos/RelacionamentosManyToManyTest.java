package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class RelacionamentosManyToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoTest() {
        Produto produto = entityManager.find(Produto.class, 1);
        Categoria categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin();
        //categoria.setProdutos(Arrays.asList(produto));
        produto.setCategorias(Arrays.asList(categoria));
        entityManager.getTransaction().commit();

        entityManager.clear();

        categoria = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertFalse(categoria.getProdutos().isEmpty());
    }
}
