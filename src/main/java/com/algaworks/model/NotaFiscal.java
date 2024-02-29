package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    //@JoinTable(name = "pedido_nota_fiscal",
      //      joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
        //    inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true)
    //)
    private Pedido pedido;

    private String xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;
}
