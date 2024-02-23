package com.algaworks.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    private int id;

    private Integer pedidotId;

    private String xml;

    private Date dataEmissao;
}
