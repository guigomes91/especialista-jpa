package com.algaworks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger {

    @NotNull
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

    @NotEmpty
    @Lob
    @Column(length = 1000, nullable = false)
    private  byte[] xml;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_emissao", nullable = false)
    private Date dataEmissao;
}
