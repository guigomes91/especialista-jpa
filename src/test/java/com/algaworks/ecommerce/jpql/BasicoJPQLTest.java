package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import com.algaworks.model.Pedido;
import com.algaworks.model.Produto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.List;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void selecionarUmAtributoParaRetorno() {
        String jpql = "select p.nome from Produto p";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> produtos = typedQuery.getResultList();
        Assert.assertTrue(String.class.equals(produtos.get(0).getClass()));

        String jpqlCliente = "select p.cliente from Pedido p";

        TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> clientes = typedQueryCliente.getResultList();
        Assert.assertTrue(Cliente.class.equals(clientes.get(0).getClass()));

        clientes.forEach(cliente -> {
            System.out.println(String.format("Cliente %s retornado.", cliente.getNome()));
        });
    }

    @Test
    public void buscarPorIdentificador() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);

        //List<Pedido> lista = typedQuery.getResultList();
        //Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void mostrarDiferencaQueries() {
        String jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido1);

        System.out.println("## Query ##");

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        Assert.assertNotNull(pedido2.getCliente());
    }
}
