package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {
    @Test
    public void agruparResultado03Exercicio() {
        //Total de vendas por cliente
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.pedido);
        Join<Pedido, Cliente> joinCliente = joinPedido.join(Pedido_.cliente);

        criteriaQuery.multiselect(
                joinCliente.get(Cliente_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto))
        ).groupBy(
                joinCliente.get(Cliente_.id)
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome cliente: " + arr[0] + ", Total venda: " + arr[1]));
    }

    @Test
    public void agruparResultado02() {
        //Total de vendas por categoria
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
        Join<Produto, Categoria> joinCategoria = joinProduto.join(Produto_.categorias);

        criteriaQuery.multiselect(
                joinCategoria.get(Categoria_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto))
        ).groupBy(
                joinCategoria.get(Categoria_.id)
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Total venda: " + arr[1]));
    }

    @Test
    public void agruparResultado01() {
        //Quantidade de produtos por categoria

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Categoria> root = criteriaQuery.from(Categoria.class);
        Join<Categoria, Produto> joinProduto = root.join(Categoria_.produtos, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Categoria_.nome),
                criteriaBuilder.count(joinProduto.get(Produto_.id))
        );

        criteriaQuery.groupBy(root.get(Categoria_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count: " + arr[1]));
    }
}
