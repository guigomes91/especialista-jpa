package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import org.hibernate.tool.schema.extract.internal.SequenceNameExtractorImpl;
import org.junit.Test;

public class EstadosECicloDeVidaTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {
        //Transient
        Categoria categoriaNovo = new Categoria();

        //Managed
        Categoria categoriaNovoGerenciada = entityManager.merge(categoriaNovo);

        //Managed
        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);

        //Removed
        entityManager.remove(categoriaGerenciada);

        //Back to Removed
        entityManager.persist(categoriaGerenciada);

        //Detach
        entityManager.detach(categoriaGerenciada);
    }
}
