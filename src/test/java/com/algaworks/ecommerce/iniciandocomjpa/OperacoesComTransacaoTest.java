package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacoesComTransacaoTest extends EntityManagerTest {
    @Test
    public void impedirOperacaoComBancoDeDados() {
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle", produto.getNome());
    }
    @Test
    public void mostrarDiferencaPersistMerge() {
        Produto produtoPersist = new Produto();
        //produtoPersist.setId(5); Comentado porque estamos utilizando IDENTITY
        produtoPersist.setNome("Smartphone One Plus");
        produtoPersist.setDescricao("O processador mais rapido.");
        produtoPersist.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        //Coloca na memória do EntityManager
        entityManager.persist(produtoPersist);
        //Aqui será realizado o update do objeto gerenciado
        produtoPersist.setNome("Smartphone Two Plus");
        entityManager.getTransaction().commit();

        entityManager.clear();

        produtoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        Assert.assertNotNull(produtoPersist);

        Produto produtoMerge = new Produto();
        //produtoMerge.setId(6); Comentado porque estamos utilizando IDENTITY
        produtoMerge.setNome("Notebook Dell");
        produtoMerge.setDescricao("O melhor da categoria.");
        produtoMerge.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        //Coloca na memória do EntityManager como uma cópia
        entityManager.merge(produtoMerge);
        //Aqui não terá efeito algum, não é uma instância gerenciada
        //Para funcionar deveriamos fazer: produtoMerge = entityManager.merge(produtoMerge);
        produtoMerge = entityManager.merge(produtoMerge);
        produtoMerge.setNome("Notebook Dell 2");

        entityManager.getTransaction().commit();

        entityManager.clear();

        produtoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoMerge);
    }
    @Test
    public void inserirObjetoComMerge() {
        Produto produto = new Produto();
        //produto.setId(4); Comentado porque estamos utilizando IDENTITY
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        //Joga o objeto na memória
        Produto produtoNew = entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.find(Produto.class, produtoNew.getId());
        Assert.assertNotNull(produto);
    }

    @Test
    public void atualizarObjetoGerenciado() {
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle Paperwhite 2ª Geração", produto.getNome());
    }

    @Test
    public void atualizarObjeto() {
        Produto produto = new Produto();
        //produto.setId(1); Comentado porque estamos utilizando IDENTITY
        produto.setNome("Kindle Paperwhite");
        //produto.setDescricao("Conheça o novo Kindle");
        //produto.setPreco(new BigDecimal(599));

        entityManager.getTransaction().begin();
        Produto produtoNew = entityManager.merge(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.find(Produto.class, produtoNew.getId());
        Assert.assertNotNull(produto);
        Assert.assertEquals("Kindle Paperwhite", produtoNew.getNome());
    }

    @Test
    public void removerObjeto() {
        Produto produto = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();

        //entityManager.clear(); Não é necessário na asserção para operação de remoção.

        Produto produtoVerificacao = entityManager.find(Produto.class, 3);
        Assert.assertNull(produtoVerificacao);
    }

    @Test
    public void inserirOPrimeiroObjeto() {
        Produto produto = new Produto();
        //produto.setId(2); Comentado porque estamos utilizando IDENTITY
        produto.setNome("Camera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        //Joga o objeto na memória
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }
    @Test
    public void abrirEFecharATransacao() {
        //Produto poduto = new Produto();
        entityManager.getTransaction().begin();

        //entityManager.persist(poduto);
        //entityManager.merge(poduto);
        //entityManager.remove(poduto);

        entityManager.getTransaction().commit();
    }
}
