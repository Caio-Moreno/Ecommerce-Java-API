package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.UsuarioCargo;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCargoResponse {
    private int _codigo;
    private String _message;
    private UsuarioCargo _cargo;
}
