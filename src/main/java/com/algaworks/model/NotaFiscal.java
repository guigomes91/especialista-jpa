package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(
            name = "pedido_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido")
    )
    //@JoinTable(name = "pedido_nota_fiscal",
      //      joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),
        //    inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true)
    //)
    private Pedido pedido;

    @Lob
    @Column(length = 1000, nullable = false)
    private  byte[] xml;

    @Column(name = "data_emissao", nullable = false)
    private Date dataEmissao;
}
