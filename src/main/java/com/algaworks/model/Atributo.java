package com.algaworks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Atributo {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    private String valor;
}
