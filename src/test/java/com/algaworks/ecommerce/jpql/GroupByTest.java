package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void agruparResultado() {
        //Quantidade de produtos por categoria
        //String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        //Total de vendas por mês
        /*String jpql = """
                select 
                    concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), 
                    sum(p.total)
                from Pedido p
                group by 
                    year(p.dataCriacao), month(p.dataCriacao)
                """;*/

        //Total de vendas por categoria
        /*String jpql = """
                select 
                    c.nome,
                    sum(ip.precoProduto)
                from
                    ItemPedido ip
                join ip.produto pro 
                join pro.categorias c
                group by
                    c.id
                """;*/

        //Total de vendas por cliente
        /*String jpql = """
                select 
                    c.nome,
                    sum(p.total)
                from
                    Pedido p
                join p.cliente c
                group by
                    c.id
                """;*/

        //Total de vendas por dia e por categoria
        String jpql = """
                select
                    concat(c.nome, ' - dia: ', day(p.dataCriacao)), 
                    sum(ip.precoProduto)
                from
                    ItemPedido ip
                join ip.pedido p
                join ip.produto pro
                join pro.categorias c
                group by
                    c.id,
                    day(p.dataCriacao)
                """;

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(li -> System.out.println(li[0] + ", " + li[1]));
    }
}
