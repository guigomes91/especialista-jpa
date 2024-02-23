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
public class Categoria {

    @EqualsAndHashCode.Include
    @Id
    private int id;

    private String nome;

    private Integer categoriaPaiId;
}
