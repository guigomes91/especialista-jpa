package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        // Java persistence query language - JPQL

        // JPQL - select p from Pedido p join p.itens i where p.id = 1 and i.precoProduto > 10

        // SQL - select p.* from pedido p join item_pedido i on i.pedido_id = p.id where p.id = 1 and i.preco_produto > 10

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);
        //Pedido pedido = typedQuery.getSingleResult();
        //Assert.assertNotNull(pedido);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
