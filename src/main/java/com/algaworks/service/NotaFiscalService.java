package com.algaworks.service;

import com.algaworks.model.Pedido;

import java.awt.event.PaintEvent;

public class NotaFiscalService {

    public void gerar(Pedido pedido) {
        System.out.println("Gerando nota para o pedido " + pedido.getId());
    }
}
