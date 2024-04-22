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
    public void pesquisarComAllTodosProdutosComMesmoPrecos() {
        //Todos os produtos que sempre foram vendidos pelo mesmo preço
        String jpql = """
                select p from Produto p where p.id not in
                (select
                    distinct pro.id
                from
                    ItemPedido ip
                join ip.produto pro
                where
                    ip.precoProduto != ALL (select p.preco from Produto p where p = ip.produto))
                """;

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny() {
        //Todos os produtos que já foram vendidos pelo menos uma vez pelo preco atual
        /*String jpql = """
                select p from Produto p where p.preco = ANY (select precoProduto from ItemPedido where produto = p)
                """;*/

        //Todos os produtos que já foram vendidos por um preco diferente do atual
        String jpql = """
                select p from Produto p where p.preco <> ALL (select precoProduto from ItemPedido where produto = p)
                """;

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll() {
        //Todos os produtos que sempre foram vendidos pelo preco atual
        /*String jpql = """
                select p from Produto p where p.preco = ALL (select precoProduto from ItemPedido where produto = p)
                """;*/

        //Todos os produtos não foram vendidos mais depois que encareceram
        String jpql = """
                select p from Produto p where p.preco > ALL (select precoProduto from ItemPedido where produto = p)
                """;

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutoSemPrecoDeVenda() {
        String jpql = """
                select
                    p
                from
                    Produto p
                where not exists
                    (select
                        1
                    from
                        ItemPedido ip
                    where p.preco = ip.precoProduto)
                """;

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComInClientesComDuasVendas() {
        String jpql = """
                select
                    c
                from
                    Cliente c
                where c.id in
                    (select
                        c.id
                    from
                        Pedido p
                    join p.cliente c
                    group by c.id
                    having count(c.id) >= 2)
                """;

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComInProdutoCategoriaDois() {
        String jpql = """
                select
                    p
                from
                    Pedido p
                where p.id in
                    (select
                        p2.id
                    from
                        ItemPedido ip2
                    join ip2.pedido p2
                    join ip2.produto pro
                    join pro.categorias cat
                    where
                        cat.id = 2)
                """;

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComExists() {
        String jpql = """
                select
                    p
                from
                    Produto p
                where exists
                (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)
                """;

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComIN() {
        String jpql = """
                select
                    p
                from
                    Pedido p
                where
                    p.id in (select p2.id from ItemPedido i2 join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)
                """;

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

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
