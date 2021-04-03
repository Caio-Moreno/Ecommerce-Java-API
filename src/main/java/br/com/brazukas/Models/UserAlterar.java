package br.com.brazukas.Models;


import br.com.brazukas.enums.TipoUsuarioInternoEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAlterar {
    private String _nome;
    private TipoUsuarioInternoEnum _tipoUser;
    private String _password;
}
