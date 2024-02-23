package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import org.junit.Assert;
import org.junit.Test;

public class PrimeiroCrudTest extends EntityManagerTest {
    @Test
    public void testInserirCliente() {
        Cliente cliente = new Cliente(3, "Guilherme");
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        cliente = entityManager.find(Cliente.class, 3);
        Assert.assertNotNull(cliente);
    }

    @Test
    public void testConsultarCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Assert.assertNotNull(cliente);
        Assert.assertEquals("Maria", cliente.getNome());
    }

    @Test
    public void testAtualizarCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        entityManager.getTransaction().begin();
        cliente.setNome("Maria Silva");
        entityManager.getTransaction().commit();

        Assert.assertEquals("Maria Silva", cliente.getNome());
    }

    @Test
    public void testRemoverCliente() {
        Cliente cliente = entityManager.find(Cliente.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

        cliente = entityManager.find(Cliente.class, 2);
        Assert.assertNull(cliente);
    }
}
