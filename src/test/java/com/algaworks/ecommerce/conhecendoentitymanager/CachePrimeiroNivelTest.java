package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Test;

public class CachePrimeiroNivelTest extends EntityManagerTest {

    @Test
    public void verificarCache() {
        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto.getNome());

        System.out.println("------------------------------");

        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        produtoResgatado.setNome("Kindle 2");
        System.out.println(produtoResgatado.getNome());
    }
}
