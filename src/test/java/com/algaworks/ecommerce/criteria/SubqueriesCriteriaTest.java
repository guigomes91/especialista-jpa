package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class SubqueriesCriteriaTest extends EntityManagerTest {
    @Test
    public void pesquisarSubqueriesComIn() {
        //String jpql = "select p from Pedido p where p.id in 
        //(select p2.id from ItemPedido i2 join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> subQueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
        Join<ItemPedido, Produto> subQueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);

        subquery.select(subQueryJoinPedido.get(Pedido_.id))
                .where(criteriaBuilder.greaterThan(subQueryJoinProduto.get(Produto_.preco), new BigDecimal(100)));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertNotNull(pedidos.isEmpty());
        pedidos.forEach(pedido -> System.out.println("ID: " + pedido.getId()));
    }

    @Test
    public void pesquisarSubqueries03() {
        //Melhores clientes
        //String jpql = "select c from Cliente c where 2150 < (select sum(p.total) from Pedido p where p.cliente = c)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class))
                .where(criteriaBuilder.equal(root, subqueryRoot.get(Pedido_.cliente)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(2150)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> clientes = typedQuery.getResultList();
        Assert.assertNotNull(clientes.isEmpty());
        clientes.forEach(cliente -> System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome()));
    }

    @Test
    public void pesquisarSubqueries02() {
        //         Todos os pedidos acima da média de vendas
        //        String jpql = "select p from Pedido p where " +
        //                " p.total > (select avg(total) from Pedido)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido>  subquerieRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.avg(subquerieRoot.get(Pedido_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertNotNull(pedidos.isEmpty());
    }

    @Test
    public void pesquisarSubqueries01() {
        //         O produto ou os produtos mais caros da base.
        //        String jpql = "select p from Produto p where " +
        //                " p.preco = (select max(preco) from Produto)";
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Produto>  subquerieRoot = subquery.from(Produto.class);
        subquery.select(criteriaBuilder.max(subquerieRoot.get(Produto_.preco)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> pedidos = typedQuery.getResultList();
        Assert.assertNotNull(pedidos.isEmpty());
    }
}
