package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ConsultaNativaTest extends EntityManagerTest {

    @Test
    public void executarSQLRetornandoEntidade() {
        String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto_loja";
        Query query = entityManager.createNativeQuery(sql, Produto.class);

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void executarSQL() {
        String sql = "select id, nome from produto";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr[0], arr[1])
        ));
    }
}
