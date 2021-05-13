package br.com.brazukas.Models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    private int _id;
    private String _nameCart;
    private String _numberCart;
    private int _idVendaFk;
    private double _valor;
    private int _qtdParcelas;
    private String _paypalEmail;
    private String _paypalCpf;
    private String _numBoleto;
    private String _mpEmail;
    private String _mpCpf;
    private String _tipo;
}
