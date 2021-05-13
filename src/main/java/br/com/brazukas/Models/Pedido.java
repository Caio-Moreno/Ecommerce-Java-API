package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pedido {
    private int _idVenda;
    private String _dataVenda;
    private double _valorTotal;
    private String _status;
}
