package br.com.brazukas.Models;

import lombok.*;

import java.util.List;


@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {
	private int _idCliente;
	private String _nome;
	private String _email;
	private String _cpf;
	private String _dataNascimento;
	private String _senha;
	private String _sexo;
	private String _telefone;
	private List<Endereco> _endereco;
}
