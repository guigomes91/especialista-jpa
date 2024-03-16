package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.mapeamentoavancado.SalvandoArquivosTest;
import com.algaworks.model.NotaFiscal;
import com.algaworks.model.PagamentoCartao;
import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPagamento;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class RelacionamentosOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoTest() {
        Pedido pedido = entityManager.find(Pedido.class, 1);
        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumeroCartao("1234");
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
        notaFiscal.setXml(carregarNotaFiscal());
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();
        pedido = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedido.getNotaFiscal());
        Assert.assertNotNull(pedido.getNotaFiscal().getXml());
    }

    private static byte[] carregarNotaFiscal() {
        try {
            return Objects.requireNonNull(SalvandoArquivosTest.class.getResourceAsStream(
                    "/nota-fiscal.xml"
            )).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
