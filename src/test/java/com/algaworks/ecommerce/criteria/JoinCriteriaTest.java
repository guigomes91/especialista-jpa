package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.ItemPedido;
import com.algaworks.model.Pagamento;
import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPagamento;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void buscarPedidoComProdutoEspecifico() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.fetch("cliente");
        root.fetch("notaFiscal", JoinType.LEFT);
        root.fetch("pagamento", JoinType.LEFT);
        Join<Pedido, ItemPedido> joinItens = root.join("itens");

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(joinItens.get("produto"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(pedido -> {
            System.out.println(
                    String.format(
                            "Pedido ID: %s, Status: %s",
                            pedido.getId(),
                            pedido.getStatus()
                    )
            );
        });
    }

    @Test
    public void usarJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.fetch("itens");
        root.fetch("notaFiscal", JoinType.LEFT);
        root.fetch("pagamento", JoinType.LEFT);
        root.fetch("cliente");
        //Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
        Assert.assertFalse(pedido.getItens().isEmpty());
    }

    @Test
    public void fazerLeftOuterJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

        criteriaQuery.select(root);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 5);
    }

    @Test
    public void fazerJoinComOn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        joinPagamento.on(criteriaBuilder.equal(
                joinPagamento.get("status"), StatusPagamento.PROCESSANDO)
        );

        criteriaQuery.select(root);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 2);
    }

    @Test
    public void fazerJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        //Join<Pedido, ItemPedido> joinItens = root.join("itens");
        //Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");

        criteriaQuery.select(root);

        /*criteriaQuery.select(joinPagamento)
            .where(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));*/

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 4);
    }
}
