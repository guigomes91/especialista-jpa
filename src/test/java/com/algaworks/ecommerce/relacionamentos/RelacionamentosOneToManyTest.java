package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RelacionamentosOneToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoTest() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(new BigDecimal(1000));
        pedido.setCliente(cliente);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        cliente = entityManager.find(Cliente.class, cliente.getId());
        itemPedido = entityManager.find(ItemPedido.class, itemPedido.getId());
        Assert.assertNotNull(itemPedido.getPedido());
        Assert.assertNotNull(itemPedido.getProduto());

        Assert.assertFalse(cliente.getPedidos().isEmpty());
    }

    @Test
    public void verificarRelacionamentoOneToManyTest() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(new BigDecimal(1000));
        pedido.setCliente(cliente);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        pedido = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertFalse(pedido.getItens().isEmpty());
    }
}
