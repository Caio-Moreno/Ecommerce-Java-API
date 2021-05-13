package br.com.brazukas.Models.Responses;

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
}
