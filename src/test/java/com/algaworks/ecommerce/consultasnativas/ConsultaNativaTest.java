package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Categoria;
import com.algaworks.model.Produto;
import com.algaworks.model.dto.CategoriaDTO;
import com.algaworks.model.dto.ProdutoDTO;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ConsultaNativaTest extends EntityManagerTest {

    @Test
    public void mapearConsultaParaDTOEmArquivoExternoExercicio() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar.dto");

        List<CategoriaDTO> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("CategoriaDTO => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarArquivoXML() {
        Query query = entityManager.createNamedQuery("ecm_categoria.listar");

        List<Categoria> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Categoria => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarUmaNamedNativeQuery02() {
        Query query = entityManager.createNamedQuery("ecm_produto.listar");

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarUmaNamedNativeQuery() {
        Query query = entityManager.createNamedQuery("produto_loja.listar");

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarColumnResultRetornarDTO() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.ProdutoDTO");

        List<ProdutoDTO> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("ProdutoDTO => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarFieldResult() {
        String sql = "select * from ecm_produto";

        Query query = entityManager.createNativeQuery(sql, "ecm_produto.Produto");

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void usarSQLResultSetMapping02() {
        String sql = "select ip.*, p.* from item_pedido ip join produto p on p.id = ip.produto_id";

        Query query = entityManager.createNativeQuery(sql, "item_pedido-produto.ItemPedido-Produto");

        List<Object[]> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", ((Produto) arr[1]).getId(), ((Produto) arr[1]).getNome())
        ));
    }

    @Test
    public void usarSQLResultSetMapping() {
                String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
                " from produto";

        Query query = entityManager.createNativeQuery(sql, "produto.Produto");

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void executarSQLComParametro() {
                String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
                " from produto where id = :id";

        Query query = entityManager.createNativeQuery(sql, Produto.class);
        query.setParameter("id", 1);

        List<Produto> lista = query.getResultList();
        lista.stream().forEach(arr -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", arr.getId(), arr.getNome())
        ));
    }

    @Test
    public void executarSQLRetornandoEntidade() {
        //        String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
//                " from produto_loja";

//        String sql = "select prd_id id, prd_nome nome, prd_descricao descricao, " +
//                "            prd_data_criacao data_criacao, prd_data_ultima_atualizacao data_ultima_atualizacao, " +
//                "            prd_preco preco, prd_foto foto " +
//                " from ecm_produto";

        String sql = "select id, nome, descricao, " +
                "            null data_criacao, null data_ultima_atualizacao, " +
                "            preco, null foto " +
                " from erp_produto";

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
