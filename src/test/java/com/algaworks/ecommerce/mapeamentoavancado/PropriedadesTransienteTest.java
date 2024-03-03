package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Cliente;
import org.junit.Assert;
import org.junit.Test;

public class PropriedadesTransienteTest extends EntityManagerTest {

    @Test
    public void validarPrimeiroNome() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assert.assertEquals("Maria", cliente.getPrimeiroNome());
    }
}
