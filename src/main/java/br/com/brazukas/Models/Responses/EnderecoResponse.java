package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.EnderecoAlterar;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoResponse {
    private int _codigo;
    private String _message;
    private List<EnderecoAlterar> _enderecos;
}
