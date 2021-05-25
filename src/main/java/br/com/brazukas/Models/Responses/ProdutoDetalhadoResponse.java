package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Endereco;
import br.com.brazukas.Models.Payment;
import br.com.brazukas.Models.VendaHasProdutoJoin;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoDetalhadoResponse {
    private int _codigo;
    private String _message;
    private List<VendaHasProdutoJoin> _produtos;
    private Payment _pagamento;
    private Endereco _endereco;

}
