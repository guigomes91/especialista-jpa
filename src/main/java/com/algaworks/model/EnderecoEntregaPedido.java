package com.algaworks.model;

import lombok.Data;

import javax.persistence.Embeddable;


@Data
@Embeddable
public class EnderecoEntregaPedido {

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
}
