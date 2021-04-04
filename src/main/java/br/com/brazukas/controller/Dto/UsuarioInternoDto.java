package br.com.brazukas.controller.Dto;

import br.com.brazukas.enums.StatusEnum;
import br.com.brazukas.enums.TipoUsuarioInternoEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsuarioInternoDto {
    private int _id;
    private String _nomeUsuarioInterno;
    private TipoUsuarioInternoEnum _tipoUsuarioInternoEnum;
    private StatusEnum _statusEnum;
}
