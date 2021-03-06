package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.Carrinho;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarrinhoResponse {
    private int _response;
    private int _quantidade;
    private String _message;
    private List<Carrinho> _carrinho;
}
