package com.algaworks.model;

import com.algaworks.listener.GenericoListener;
import com.algaworks.listener.GerarNotaFiscalListener;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Pedido.dadosEssenciais",
                attributeNodes = {
                        @NamedAttributeNode("dataCriacao"),
                        @NamedAttributeNode("status"),
                        @NamedAttributeNode("total"),
                        @NamedAttributeNode(
                                value = "cliente",
                                subgraph = "cli"
                        )
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "cli",
                                attributeNodes = {
                                        @NamedAttributeNode("nome"),
                                        @NamedAttributeNode("cpf")
                                }
                        )
                }
        )
})
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger {

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cliente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pedido_cliente")
    )
    private Cliente cliente;

    @NotNull
    @PastOrPresent
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @FutureOrPresent
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
    private NotaFiscal notaFiscal;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Valor total inválido")
    @Column(nullable = false)
    private BigDecimal total;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
    private Pagamento pagamento;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itens;

    public boolean isPago() {
        return StatusPedido.PAGO.equals(status);
    }

    public void calcularTotal() {
        if (CollectionUtils.isNotEmpty(this.itens)) {
            total = itens.stream().map(
                        item -> new BigDecimal(item.getQuantidade()).multiply(item.getPrecoProduto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            total = BigDecimal.ZERO;
        }
    }

    @PrePersist
    public void aoPersistir() {
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PostPersist
    public void aposPersistir() {
        calcularTotal();
        System.out.println("Após persistir Pedido.");
    }

    @PreUpdate
    public void aoAtualizar() {
        dataUltimaAtualizacao = LocalDateTime.now();
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o Pedido.");
    }
}
