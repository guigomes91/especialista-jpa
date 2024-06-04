package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SecondaryTable(
        name = "cliente_detalhe",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"),
        foreignKey = @ForeignKey(name = "fk_cliente_detalhe_cliente")
)
@AllArgsConstructor
@NoArgsConstructor
@NamedStoredProcedureQuery(
        name = "compraram_acima_media", procedureName = "compraram_acima_media",
        parameters = {
            @StoredProcedureParameter(name = "ano", type = Integer.class, mode = ParameterMode.IN)
        },
        resultClasses = Cliente.class)
@Entity
@Table(
        name = "cliente",
        uniqueConstraints = { @UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) },
        indexes = { @Index(name = "idx_cliente_nome", columnList = "nome") }
)
public class Cliente extends EntidadeBaseInteger {

    @NotBlank
    @Column(length = 14, nullable = false)
    private String cpf;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @ElementCollection
    @CollectionTable(
            name = "cliente_contato",
            joinColumns = @JoinColumn(
                    name = "cliente_id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "fk_cliente_contato")
            )
    )
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @Transient
    private String primeiroNome;

    @Column(table = "cliente_detalhe", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Column(
            name = "data_nascimento",
            table = "cliente_detalhe"
    )
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @PostLoad
    public void configurarPrimeiroNome() {
        if (nome != null && !nome.isBlank()) {
            int index = nome.indexOf(" ");
            if (index > -1) {
                primeiroNome = nome.substring(0, index);
            }
        }
    }
}
