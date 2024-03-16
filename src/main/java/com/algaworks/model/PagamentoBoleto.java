package com.algaworks.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("boleto")
@Entity
@Table(name = "pagamento_boleto")
public class PagamentoBoleto extends Pagamento {

    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;
}
