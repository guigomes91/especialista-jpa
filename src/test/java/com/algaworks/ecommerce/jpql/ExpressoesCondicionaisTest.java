package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import com.algaworks.model.Pedido;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void usarExpressaoIN() {
        Cliente cliente1 = new Cliente(); // entityManager.find(Cliente.class, 1);
        cliente1.setId(1);

        Cliente cliente2 = new Cliente(); // entityManager.find(Cliente.class, 2);
        cliente2.setId(2);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        String jpql = "select p from Pedido p where p.cliente in (:clientes)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoCase() {
        String jpql = """
                select
                    p.id,
                    case type(p.pagamento)
                    when PagamentoBoleto then 'Pago com boleto'
                    when PagamentoCartao then 'Pago com cartao'
                    else 'Não pago ainda.' end
                from
                    Pedido p
                """;

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(li -> System.out.println(li[0] + ", " + li[1]));
    }

    @Test
    public void usarBetween() {
        String jpql = "select p from Pedido p where p.dataConclusao between :dataInicial and :dataFinal";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("dataFinal", LocalDateTime.now());

        List<Pedido> pedidos = typedQuery.getResultList();
        System.out.println(pedidos.get(0).getTotal());

        Assert.assertEquals(pedidos.size(), 1);
    }

    @Test
    public void usarMaiorMenorEmData() {
        String jpql = "select p from Pedido p where p.dataConclusao >= :dataInicial and p.dataConclusao <= :dataFinal";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(2));
        typedQuery.setParameter("dataFinal", LocalDateTime.now());

        List<Pedido> pedidos = typedQuery.getResultList();
        System.out.println(pedidos.get(0).getTotal());

        Assert.assertEquals(pedidos.size(), 1);
    }

    @Test
    public void usarMaiorMenor() {
        String jpql = "select p from Produto p where p.preco >= :precoInicial and p.preco <= :precoFinal";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        List<Produto> produtos = typedQuery.getResultList();
        produtos.forEach(produto -> System.out.println("nome produto: " + produto.getNome()));

        Assert.assertFalse(produtos.isEmpty());
    }

    @Test
    public void usarExpressaoCondicionalLike() {
        String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        typedQuery.setParameter("nome", "o");
        List<Cliente> pedidos = typedQuery.getResultList();

        System.out.print("Nome: " + pedidos.get(0).getNome());
        Assert.assertFalse(pedidos.isEmpty());
    }

    @Test
    public void usarIsNull() {
        String jpql = "select p from Produto p where p.foto is null";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> produtos = typedQuery.getResultList();

        System.out.print("Nome: " + produtos.get(0).getNome());
        Assert.assertFalse(produtos.isEmpty());
    }

    @Test
    public void usarIsEmpty() {
        String jpql = "select p from Produto p where p.categorias is empty";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> produtos = typedQuery.getResultList();
        Assert.assertFalse(produtos.isEmpty());
    }
}
