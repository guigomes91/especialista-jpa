package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import com.algaworks.model.EnderecoEntregaPedido;
import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPedido;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void testAnalisarMapeamentoObjetoEmbutido() {
        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
        endereco.setCep("00000-00");
        endereco.setLogradouro("Rua das Laranjeiras");
        endereco.setNumero("123");
        endereco.setCidade("Uberlandia");
        endereco.setBairro("Centro");
        endereco.setEstado("MG");

        Cliente cliente = entityManager.find(Cliente.class, 2);

        Pedido pedido = new Pedido();
        //pedido.setId(1); Comentado porque estamos utilizando IDENTITY
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);
        pedido.setCliente(cliente);
        pedido.setDataConclusao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();
        pedido = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedido);
        Assert.assertNotNull(pedido.getEnderecoEntrega());
    }
}
