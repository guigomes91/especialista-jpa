package com.algaworks.model;

import com.algaworks.listener.GenericoListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
        @NamedQuery(name = "Produto.listarPorCategoria", query =
                "select p from Produto p where exists " +
                        "(select 1 from Categoria c2 join c2.produtos p2 where p2 = p and c2.id = :categoria)"
        )
})
@EntityListeners({ GenericoListener.class })
@Entity
@Table(
        name = "produto",
        uniqueConstraints = { @UniqueConstraint(name = "unq_nome", columnNames = { "nome" }) },
        indexes = { @Index(name = "idx_nome", columnList = "nome") }
)
public class Produto extends EntidadeBaseInteger {

    @Column(length = 100, nullable = false)
    private String nome;

    @Lob // descricao longtext
    private String descricao;

    private BigDecimal preco;

    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @Lob
    @Column(length = 1000)
    private byte[] foto;

    @ManyToMany
    @JoinTable(name = "produto_categoria",
            joinColumns = @JoinColumn(
                    name = "produto_id",
                    foreignKey = @ForeignKey(name = "fk_produto_categoria_produto")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "categoria_id",
                    foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria"),
                    nullable = false
            )
    )
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection
    @CollectionTable(
            name = "produto_tag",
            joinColumns = @JoinColumn(
                    name = "produto_id",
                    foreignKey = @ForeignKey(name = "fk_produto_tag"),
                    nullable = false
            )
    )
    @Column(length = 50, name = "tag", nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(
            name = "produto_atributo",
            joinColumns = @JoinColumn(
                    name = "produto_id",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "fk_produto_atributo")
            )
    )
    private List<Atributo> atributos;
}
