package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger {

    @NotNull
    @OneToOne(optional = false)
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_estoque_produto"))
    private Produto produto;

    @PositiveOrZero
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
}
