package com.algaworks.model;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn(
        name = "tipo_pagamento",
        discriminatorType = DiscriminatorType.STRING
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "pagamento")
public abstract class Pagamento extends EntidadeBaseInteger {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(
            name = "pedido_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pagamento_pedido")
    )
    private Pedido pedido;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
}
