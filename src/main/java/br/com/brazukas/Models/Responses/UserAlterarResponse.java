package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.UserAlterar;
import br.com.brazukas.controller.Dto.UsuarioInternoDto;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAlterarResponse {

        private int _response;
        private String _message;
        private List<UserAlterar> _usuario;
}
