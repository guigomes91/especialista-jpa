package com.algaworks.ecommerce.cache;

import com.algaworks.model.Pedido;
import org.junit.*;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import java.util.HashMap;
import java.util.Map;

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() { entityManagerFactory.close(); }

    @Test
    public void controlarCacheDinamicamente() {
        //javax.persistence.cache.retriveMode CacheRetrieveMode = ajuda a controlar se queremos usar o cache ou não na hora da pesquisa
        //javax.persistence.cache.storeMode CacheStoreMode = Na hora da pesquisa eu quero pegar o resultado e colocar no cache ou não

        Cache cache = entityManagerFactory.getCache();

        System.out.println("Buscando todos os pedidos........................ ");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        entityManager1.createQuery(
                "select p from Pedido p", Pedido.class)
                //.setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
                .getResultList();

        System.out.println("Buscando o pedido de ID igual a 2........................ ");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Map<String, Object> propriedades = new HashMap<>();
        //propriedades.put("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        //propriedades.put("jakarta.persistence.cache.retrieveMode", CacheStoreMode.BYPASS);
        entityManager2.find(Pedido.class, 2, propriedades);

        System.out.println("Buscando todos os pedidos (de novo)........................ ");
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        entityManager3.createQuery(
                        "select p from Pedido p", Pedido.class)
                //.setHint("javax.persistence.cache.retrieveMode", CacheStoreMode.BYPASS)
                .getResultList();
    }

    @Test
    public void analisarOpcoesCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1: ");
        entityManager1.createQuery("select p from Pedido p",
                Pedido.class).getResultList();

        Assert.assertTrue(cache.contains(Pedido.class, 1));
    }

    @Test
    public void verificarSeEstaNoCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1: ");
        entityManager1.createQuery("select p from Pedido p",
                Pedido.class).getResultList();

        Assert.assertTrue(cache.contains(Pedido.class, 1));
        Assert.assertTrue(cache.contains(Pedido.class, 2));
    }

    @Test
    public void removerDoCache() {
        Cache cache = entityManagerFactory.getCache();
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1: ");
        entityManager1.createQuery("select p from Pedido p",
                Pedido.class).getResultList();

        System.out.println("Removendo do cache: ");
        cache.evictAll();

        System.out.println("Buscando a partir da instância 2: ");
        entityManager2.find(Pedido.class, 1);
        entityManager2.find(Pedido.class, 2);
    }

    @Test
    public void adicionarPedidoNoCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1: ");
        entityManager1.createQuery("select p from Pedido p join fetch p.notaFiscal nf join fetch p.pagamento pag",
                Pedido.class).getResultList();

        System.out.println("Buscando a partir da instância 2: ");
        entityManager2.find(Pedido.class, 1);
    }

    @Test
    public void buscarDoCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1: ");
        entityManager1.find(Pedido.class, 1);

        System.out.println("Buscando a partir da instância 2: ");
        entityManager2.find(Pedido.class, 1);
    }
}
