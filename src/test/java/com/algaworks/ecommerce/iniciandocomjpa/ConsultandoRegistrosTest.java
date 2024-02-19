package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class ConsultandoRegistrosTest extends EntityManagerTest {
    @Test
    public void testBuscarPorIdentificador() {
        //Produto produto = entityManager.find(Produto.class, 1);

        /*
            Quando pede por referencia, enquanto não usar um atributo do objeto,
            o hibernate não vai buscar na base de dados
         */
        Produto produto = entityManager.getReference(Produto.class, 1);

        System.out.println("Ainda não buscou no banco de dados!");

        Assert.assertNotNull(produto);
        Assert.assertEquals("Kindle", produto.getNome());
    }

    @Test
    public void testAtualizarAReferencia() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");

        //Reiniciar a entidade que acabamos de buscar no banco de dados
        entityManager.refresh(produto);

        Assert.assertEquals("Kindle", produto.getNome());
    }
}
