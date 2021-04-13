package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Cliente;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteResponse {
    private int _codigo;
    private String _message;
    private List<Cliente> _clientes;
}
