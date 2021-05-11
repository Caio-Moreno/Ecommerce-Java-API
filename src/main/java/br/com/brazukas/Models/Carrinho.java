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
    private String _status;
    private int _quantidade;
    private double _valor;
    private String _sessionId;
    private String _imageProduto;
    private String _nomeProduto;
}