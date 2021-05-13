package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Pedido;
import br.com.brazukas.Models.VendaHasProdutoJoin;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaHasProdutoJoinResponse {
    private int _codigo;
    private String _message;
    private List<VendaHasProdutoJoin> _pedidos;
}