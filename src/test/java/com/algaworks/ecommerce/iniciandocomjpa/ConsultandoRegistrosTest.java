package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.model.Produto;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConsultandoRegistrosTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    @Before
    public void setUP() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }

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
