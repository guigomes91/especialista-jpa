package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto",
                entities = {@EntityResult(entityClass = ItemPedido.class), @EntityResult(entityClass = Produto.class)})
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Version
    private Integer versao;

    @EmbeddedId
    private ItemPedidoId id;

    @NotNull
    @MapsId("pedidoId")
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "pedido_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_pedido")
    )
    private Pedido pedido;

    @NotNull
    @MapsId("produtoId")
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_produto")
    )
    private Produto produto;

    @PositiveOrZero
    @NotNull
    @Digits(integer = 10, fraction = 2, message = "Limites não permitido para este preço")
    @Column(name = "preco_produto", nullable = false)
    private BigDecimal precoProduto;

    @NotNull
    @Min(value = 1, message = "Minimo para o estoque deve ser 1")
    @Column(nullable = false)
    private Integer quantidade;
}
