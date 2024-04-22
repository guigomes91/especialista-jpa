package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Test;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacoesEmLoteTest extends EntityManagerTest {

    private static final int LIMITE_INSERCOES_EM_MEMORIA = 4;

    @Test
    public void atualizarEmLote() {
        String jpql = """
                    update
                        Produto p
                    set p.preco = p.preco + (p.preco * 0.1)
                    where exists
                        (select 1 from p.categorias c2 where c2.id = :categoria)
                """;

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(jpql);
        query.setParameter("categoria", 2);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    public void inserirEmLote() throws IOException {
        InputStream in = OperacoesEmLoteTest.class.getClassLoader()
                .getResourceAsStream("produtos/importar.txt");

        assert in != null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            entityManager.getTransaction().begin();

            int contadorInsercoes = 0;

            for (String linha : reader.lines().toList()) {
                if (linha.isBlank()) {
                    continue;
                }

                String[] produtoColuna = linha.split(";");
                Produto produto = new Produto();
                produto.setNome(produtoColuna[0]);
                produto.setDescricao(produtoColuna[1]);
                produto.setPreco(new BigDecimal(produtoColuna[2]));
                produto.setDataCriacao(LocalDateTime.now());

                entityManager.persist(produto);

                if (++contadorInsercoes == LIMITE_INSERCOES_EM_MEMORIA) {
                    entityManager.flush();
                    entityManager.clear();

                    contadorInsercoes = 0;

                    System.out.println("----------------------------------------");
                }
            }
        }

        entityManager.getTransaction().commit();
    }
}
