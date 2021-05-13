package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Payment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagamentoResponse {
    private int _httpCode;
    private String _message;
    private int _idVenda;
    private String _numPedido;
    private Payment _pagamento;
}
