package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda {
    private int _idVenda;
    private String _dataVenda;
    private int _idCliente;
    private int _quantidade;
    private double _valorTotal;
    private String _status;
    private int _idEndereco;

    public Venda(int _idVenda, String _nome,int _quantidade, double _valorTotal, String _dataVenda){
        this._idVenda = _idVenda;
        this._status = _nome;
        this._quantidade = _quantidade;
        this._valorTotal = _valorTotal;
        this._dataVenda = _dataVenda;
    }
}
