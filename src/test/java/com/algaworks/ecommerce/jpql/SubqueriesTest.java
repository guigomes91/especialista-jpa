package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import com.algaworks.model.Pedido;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class SubqueriesTest extends EntityManagerTest {
    @Test
    public void pesquisarSubqueries() {
        //O produto ou os produtos mais caros da base
        /*String jpql = """
                select
                    p
                from
                    Produto p
                where
                    p.preco = (select max(preco) from Produto)
                """;*/

        //Os pedidos acima da média de vendas
        /*String jpql = """
                select
                    p
                from
                    Pedido p
                where
                    p.total > (select avg(total) from Pedido)
                """;*/

        //Bon clientes. Versão 1
        /*String jpql = """
                select
                    c
                from
                    Cliente c
                where
                    500 < (select sum(total) from c.pedidos)
                """;*/

        //Bon clientes. Versão 2
        String jpql = """
                select
                    c
                from
                    Cliente c
                where
                    500 < (select
                              sum(p.total)
                           from
                              Pedido p
                           where
                              p.cliente = c
                           )
                """;

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(cliente -> System.out.println("ID: " + cliente.getId() + ", nome: " + cliente.getNome()));
    }
}
