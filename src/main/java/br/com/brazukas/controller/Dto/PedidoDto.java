package br.com.brazukas.controller.Dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoDto {

	private int _idVenda;
	private String _dataVenda;
	private double _valorTotal;
	private String _status;
	private String _numPedido;

}
