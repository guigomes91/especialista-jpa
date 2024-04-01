package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Pedido;
import com.algaworks.model.dto.ProdutoDTO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void fazerJoin() {
        String jpql = "select pro from Pedido p join p.itens i join i.produto pro where pro.id = 1";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 2);
    }
}
