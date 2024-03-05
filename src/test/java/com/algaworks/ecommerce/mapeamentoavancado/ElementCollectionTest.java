package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Atributo;
import com.algaworks.model.Cliente;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void aplicarTags() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class, 1);
        produto.setTags(Arrays.asList("e-book", "livro-digital"));

        entityManager.getTransaction().commit();
        entityManager.clear();

        produto = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(2, produto.getTags().size());
        Assert.assertFalse(produto.getTags().isEmpty());
    }

    @Test
    public void aplicarAtributos() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class, 1);
        produto.setTags(Arrays.asList("e-book", "livro-digital"));
        produto.setAtributos(List.of(new Atributo("tela", "320x600")));

        entityManager.getTransaction().commit();
        entityManager.clear();

        produto = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(2, produto.getTags().size());
        Assert.assertEquals(1, produto.getAtributos().size());
        Assert.assertFalse(produto.getTags().isEmpty());
    }

    @Test
    public void aplicarContatos() {
        entityManager.getTransaction().begin();

        Cliente cliente = entityManager.find(Cliente.class, 1);
        cliente.setContatos(Collections.singletonMap("email", "maria.jose@mail.com"));

        entityManager.getTransaction().commit();
        entityManager.clear();

        cliente = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertEquals(
                "maria.jose@mail.com",
                cliente.getContatos().get("email")
        );
    }
}
