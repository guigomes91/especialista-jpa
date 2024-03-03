package com.algaworks.model;

import com.algaworks.listener.GenericoListener;
import com.algaworks.listener.GerarNotaFiscalListener;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
@Entity
@Table(name = "pedido")
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao;

    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @OneToOne(mappedBy = "pedido")
    private PagamentoCartao pagamento;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<ItemPedido> itens;

    public boolean isPago() {
        return StatusPedido.PAGO.equals(status);
    }

    //@PrePersist
    //@PreUpdate
    public void calcularTotal() {
        if (CollectionUtils.isNotEmpty(this.itens)) {
            total = itens.stream().map(ItemPedido::getPrecoProduto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
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
