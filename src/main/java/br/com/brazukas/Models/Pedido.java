package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pedido {
    private String _numPedido;
	private int _idVenda;
    private String _dataVenda;
    private double _valorTotal;
    private String _status;
    private int _idEntrega;
}
