package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {
    @Test
    public void usarExpressaoDiferente() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.notEqual(root.get(Pedido_.total), new BigDecimal(499)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(pedido ->
                System.out.println(
                        "ID: " + pedido.getId() + ", Data: " + pedido.getDataCriacao() + ", Total: " + pedido.getTotal()
                )
        );
    }

    @Test
    public void usarBetween() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.between(root.get(Pedido_.total), new BigDecimal(499), new BigDecimal(2398)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(pedido -> System.out.println("ID: " + pedido.getId() + ", Data: " + pedido.getDataCriacao()));
    }

    @Test
    public void usarMaiorMenorComDatas() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.greaterThan(root.get(Pedido_.dataCriacao), LocalDateTime.now().minusDays(4)),
                        criteriaBuilder.lessThanOrEqualTo(root.get(Pedido_.dataCriacao), LocalDateTime.now()));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(pedido -> System.out.println("ID: " + pedido.getId() + ", Data: " + pedido.getDataCriacao()));
    }

    @Test
    public void usarMaiorMenor() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.greaterThanOrEqualTo(
                            root.get(Produto_.preco), new BigDecimal("2000.0")),
                        criteriaBuilder.lessThan(
                            root.get(Produto_.preco), new BigDecimal("5000.0")));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> produtos = typedQuery.getResultList();
        Assert.assertFalse(produtos.isEmpty());

        produtos.forEach(produto -> System.out.println("ID: " + produto.getId() + ", Nome: " + produto.getNome() + ", Preco: " + produto.getPreco()));
    }

    @Test
    public void usarIsEmpty() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root).where(criteriaBuilder.isEmpty(root.get(Produto_.categorias)));
        //criteriaQuery.select(root).where(root.get(Produto_.categorias).isNull()); Isso não roda, da problema is null com coleções

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> produtos = typedQuery.getResultList();

        System.out.print("Nome: " + produtos.getFirst().getNome());
        Assert.assertFalse(produtos.isEmpty());
    }

    @Test
    public void usarIsNull() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        //criteriaQuery.select(root).where(root.get(Produto_.foto).isNull());
        criteriaQuery.select(root).where(criteriaBuilder.isNull(root.get(Produto_.foto)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> produtos = typedQuery.getResultList();

        System.out.print("Nome: " + produtos.getFirst().getNome());
        Assert.assertFalse(produtos.isEmpty());
    }

    @Test
    public void usarExpressaoCondicionalLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.nome), "%a%"));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Cliente> clientes = typedQuery.getResultList();

        System.out.print("Nome: " + clientes.get(0).getNome());
        Assert.assertFalse(clientes.isEmpty());
    }
}
