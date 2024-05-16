package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "categoria",
        uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" }) }
)
public class Categoria extends EntidadeBaseInteger {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(
            name = "categoria_pai_id",
            foreignKey = @ForeignKey(name = "fk_categoria_pai")
    )
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
}
