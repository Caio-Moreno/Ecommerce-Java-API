package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.UsuarioInterno;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioInternoInserirResponse {
    private int _response;
    private String _message;
    private List<UsuarioInterno> _usuario;
}
