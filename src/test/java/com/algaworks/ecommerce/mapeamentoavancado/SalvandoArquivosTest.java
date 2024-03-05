package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.NotaFiscal;
import com.algaworks.model.Pedido;
import com.algaworks.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

public class SalvandoArquivosTest extends EntityManagerTest {

    @Test
    public void salvarXmlNota() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(loadFile("nota-fiscal.xml"));

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        notaFiscal = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        Assert.assertNotNull(notaFiscal.getXml());
        Assert.assertTrue(notaFiscal.getXml().length > 0);

        /*try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(
                            System.getProperty("user.home") + "/xml.xml")).toFile());
            out.write(notaFiscal.getXml());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    @Test
    public void salvarFotoProduto() {
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setFoto(loadFile("raca-de-cachorro-pequeno.jpg"));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        produto = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produto.getFoto());
        Assert.assertTrue(produto.getFoto().length > 0);

        try {
            OutputStream out = new FileOutputStream(
                    Files.createFile(Paths.get(
                            System.getProperty("user.home") + "/raca-de-cachorro-pequeno.jpg")).toFile());
            out.write(produto.getFoto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] loadFile(String fileName) {
        try {
            return Objects.requireNonNull(SalvandoArquivosTest.class.getResourceAsStream(
                    String.format("/%s", fileName)
            )).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
