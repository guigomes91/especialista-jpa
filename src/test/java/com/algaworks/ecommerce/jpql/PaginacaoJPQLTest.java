package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class PaginacaoJPQLTest extends EntityManagerTest {
    @Test
    public void usarPaginacao() {
        String jpql = "select c from Categoria c order by c.nome";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);

        // FIRST_RESULT = MAX_RESULT * (pagina - 1)
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(4);

        List<Categoria> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(categoria -> System.out.println(categoria.getId() + " - " + categoria.getNome()));
    }
}
