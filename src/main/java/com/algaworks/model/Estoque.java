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
public class Estoque {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer produtoId;

    private Integer quantidade;
}
