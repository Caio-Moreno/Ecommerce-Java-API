package br.com.brazukas.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Data
@Getter
@Setter
@ToString
public class Cliente {
	private String _nome;
	private String _email;
	private String _cpf;
	private String _dataNascimento;
	private String _senha;
	private String _sexo;
	private String _telefone;
	private List<Endereco> _endereco;
}
