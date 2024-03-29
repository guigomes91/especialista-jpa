package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import com.algaworks.model.SexoCliente;
import org.junit.Assert;
import org.junit.Test;

public class MapeamentoEnumeracoesTest extends EntityManagerTest {
    @Test
    public void testarEnum() {
        Cliente cliente = new Cliente();
        //cliente.setId(4); Comentado porque estamos utilizando IDENTITY
        cliente.setNome("José Mineiro");
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setCpf("123131231321");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao);
    }
}
