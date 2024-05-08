package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class SubqueriesCriteriaTest extends EntityManagerTest {

    @Test
    public void pesquisarComAllExercicio() {
        /*
        Todos os produtos que sempre foram vendidos pelo mesmo preço
            select distinct p from ItemPedido ip join ip.produto p where ip.precoProduto = ALL (select precoProduto from ItemPedido where produto = p and id <> ip.id)
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);

        criteriaQuery.select(root.get(ItemPedido_.produto));

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);

        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto))
                .where(
                        criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root.get(ItemPedido_.produto)),
                        criteriaBuilder.notEqual(subqueryRoot.get(ItemPedido_.id), root.get(ItemPedido_.id))
                );

        criteriaQuery.where(
                criteriaBuilder.equal(root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery))
        ).distinct(true);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny02() {
        /*
        Todos os produtos que já foram vendidos por um preco diferente do atual
            select p from Produto p where p p.preco <> ANY (select precoProduto from ItemPedido where produto = p)
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto))
                .where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.notEqual(root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny01() {
        /*
        Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preco atual
            select p from Produto p where p p.preco = ANY (select precoProduto from ItemPedido where produto = p)
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto))
                .where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll02() {
        /*
        Todos os produtos que não foram vendidos mais depois que encareceram
            select p from Produto p where p.perco > ALL (select precoProduto from ItemPedido where produto = p)
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto))
                .where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(root.get(Produto_.preco), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll01() {
        /*
        Todos os produtos que sempre foram vendidos pelo preco atual
            select p from Produto p where p.perco = ALL (select precoProduto from ItemPedido where produto = p)
         */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto))
                .where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.all(subquery)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComExistsExercicio() {
        //Pesquisar todos produtos que já foram vendidos por um preço diferente do preço atual

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = subqueryRoot.join(ItemPedido_.produto);

        subquery.select(criteriaBuilder.literal(1))
                .where(
                        criteriaBuilder.notEqual(subqueryRoot.get(ItemPedido_.precoProduto), joinProduto.get(Produto_.preco)),
                        criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root)
                );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComINExercicio() {
        //Pesquisar pedidos que tem algum produto da categoria 2

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<Pedido> rootSubquery = subquery.from(Pedido.class);
        Join<Pedido, ItemPedido> joinItemPedido = rootSubquery.join(Pedido_.itens);
        Join<ItemPedido, Produto> joinProduto = joinItemPedido.join(ItemPedido_.produto);
        Join<Produto, Categoria> joinCategoria = joinProduto.join(Produto_.categorias);

        subquery
                .select(rootSubquery.get(Pedido_.id))
                .where(
                        criteriaBuilder.equal(joinCategoria.get(Categoria_.id), 2)
                );

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertNotNull(pedidos.isEmpty());
        pedidos.forEach(pedido -> System.out.println("ID: " + pedido.getId()));
    }

    @Test
    public void pesquisarComSubqueryExercicio() {
        //Todos clientes que já fizeram mais de 2 pedidos

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<Pedido> rootSubquery = subquery.from(Pedido.class);
        subquery
                .select(rootSubquery.get(Pedido_.cliente).get(Cliente_.id))
                .groupBy(rootSubquery.get(Pedido_.cliente).get(Cliente_.id))
                .having(
                        criteriaBuilder.greaterThan(
                                criteriaBuilder.count(rootSubquery.get(Pedido_.cliente).get(Cliente_.id)),
                                Long.valueOf(2)
                        )
                );

        criteriaQuery.where(root.get(Cliente_.id).in(subquery));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> clientes = typedQuery.getResultList();
        Assert.assertNotNull(clientes.isEmpty());
        clientes.forEach(cliente -> System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome()));
    }

    @Test
    public void pesquisarComExists() {
        /*String jpql = """
                select
                    p
                from
                    Produto p
                where exists
                (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)
                """;*/

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        root.fetch(Produto_.estoque, JoinType.LEFT);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1))
                .where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertNotNull(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarSubqueriesComIn() {
        //String jpql = "select p from Pedido p where p.id in
        //(select p2.id from ItemPedido i2 join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.fetch(Pedido_.cliente, JoinType.LEFT);
        root.fetch(Pedido_.notaFiscal, JoinType.LEFT);
        root.fetch(Pedido_.pagamento, JoinType.LEFT);

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
