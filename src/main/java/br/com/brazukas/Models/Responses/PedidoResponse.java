package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.Login;
import br.com.brazukas.Models.Pedido;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoResponse {
    private int _codigo;
    private String _message;
    private List<Pedido> _pedidos;
}
