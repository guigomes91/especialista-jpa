package com.algaworks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class EnderecoEntregaPedido {

    @Column(length = 9)
    private String cep;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 50)
    private String complemento;
}
