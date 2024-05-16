package com.algaworks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("boleto")
@Entity
@Table(name = "pagamento_boleto")
public class PagamentoBoleto extends Pagamento {

    @NotBlank
    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    @NotNull
    @PastOrPresent(message = "Data de vencimento inválida")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
}
