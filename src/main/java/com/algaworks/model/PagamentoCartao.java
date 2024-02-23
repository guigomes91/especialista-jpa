package com.algaworks.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PagamentoCartao {

    @EqualsAndHashCode.Include
    @Id
    private int id;

    private Integer pedidoId;

    private StatusPagamento status;
    private String numero;
}
