package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Venda;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaResponse {
    private int _response;
    private int _quantidade;
    private String _message;
    private List<Venda> _vendas;
}
