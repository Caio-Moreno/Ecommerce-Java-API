package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.Login;
import br.com.brazukas.Models.UsuarioStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioStatusResponse {
    private int _codigo;
    private String _message;
    private UsuarioStatus _status;
}
