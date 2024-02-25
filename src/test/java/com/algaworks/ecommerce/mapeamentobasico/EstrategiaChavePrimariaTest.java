package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.Assert;
import org.junit.Test;

public class EstrategiaChavePrimariaTest extends EntityManagerTest {

    @Test
    public void testEstrategiaAuto() {
        Categoria categoria = new Categoria();
        categoria.setNome("Eletronico");

        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        categoria = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoria);
    }
}
