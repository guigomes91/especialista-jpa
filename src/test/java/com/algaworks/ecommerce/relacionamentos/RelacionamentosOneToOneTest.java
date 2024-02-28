package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class RelacionamentosOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoTest() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumero("1234");
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();
        pedido = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedido.getPagamento());
    }

    @Test
    public void verificarRelacionamentoNotaEPedidoTest() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setXml("123455");
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();
        pedido = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedido.getNotaFiscal());
        Assert.assertEquals("123455", pedido.getNotaFiscal().getXml());
    }
}
