package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import org.hibernate.tool.schema.extract.internal.SequenceNameExtractorImpl;
import org.junit.Test;

public class EstadosECicloDeVidaTest extends EntityManagerTest {

    @Test
    public void analisarEstados() {
        //Transiente
        Categoria categoriaNovo = new Categoria();

        //Gerenciada
        Categoria categoriaNovoGerenciada = entityManager.merge(categoriaNovo);

        //Gerenciada
        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);

        //Removida
        entityManager.remove(categoriaGerenciada);

        //Voltou para gerenciada
        entityManager.persist(categoriaGerenciada);

        //Desanexada
        entityManager.detach(categoriaGerenciada);
    }
}
