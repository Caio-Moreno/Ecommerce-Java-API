package br.com.brazukas.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Data
@Getter
@Setter
@ToString

public class Cliente {
	
	private int _idCliente;
	private String _nome;
	private String _cpf;
	private String _sexo;
	private String _dataNascimento;
	private String _telefone;
	private String _email;
	private String _cep;
	private String _endereco;
	private String _bairro;
	private int _numero;
	private String _complemento;
	private String _cidade;
	private String _estado;
	
	public Cliente(int _idCliente, String _nome, String _cpf, String _sexo, String _dataNascimento, String _telefone,
			String _email, String _cep, String _endereco, String _bairro, int _numero, String _complemento,
			String _cidade, String _estado) {
		
		this._idCliente = _idCliente;
		this._nome = _nome;
		this._cpf = _cpf;
		this._sexo = _sexo;
		this._dataNascimento = _dataNascimento;
		this._telefone = _telefone;
		this._email = _email;
		this._cep = _cep;
		this._endereco = _endereco;
		this._bairro = _bairro;
		this._numero = _numero;
		this._complemento = _complemento;
		this._cidade = _cidade;
		this._estado = _estado;
	}
	
	

}
