package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.model.Pedido;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

public class EagerELazyTest extends EntityManagerTest {

    @Test
    public void verificarComportamentoTest() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        //Busca assim que o find retornar a instância de pedido
        pedido.getCliente();
        System.out.println("Hibernate ainda não buscou no banco a listagem de itens...\n\n");
        //A lista de itens ainda não terá os registros, será feito a busca no banco na hora de usar os itens, o hibernate economiza a pesquisa
        pedido.getItens();
        System.out.println("Chamada de pedido.getItens()... Hibernate ainda não buscou no banco");
        pedido.getItens().isEmpty();
        System.out.println("Chamada de pedido.getItens().isEmpty()... Hibernate buscou no banco");
    }
}
