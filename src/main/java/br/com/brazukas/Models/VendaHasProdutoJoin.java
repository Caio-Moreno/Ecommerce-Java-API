package br.com.brazukas.Models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaHasProdutoJoin {
    private int _idProduto;
    private double _valor;
    private int _quantidade;
    private String _nome;

}
