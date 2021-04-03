package br.com.brazukas.Models;

import br.com.brazukas.enums.StatusEnum;
import br.com.brazukas.enums.TipoUsuarioInternoEnum;
import lombok.*;


@Data
@Getter
@Setter
@ToString
@Builder
public class UsuarioInterno {

	private int _id;
	private String _nome;
	private String _cpf;
	private String _sexo;
	private String _dataNascimento;
	private String _email;
	private String _password;
	private StatusEnum _status;
	private TipoUsuarioInternoEnum _tipoUser;
}
