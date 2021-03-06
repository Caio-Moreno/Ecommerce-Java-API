package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carrinho {
    private int _idCliente;
    private int _idProduto;
    private String _nomeProduto;
    private int _quantidade;
    private double _valor;
}
