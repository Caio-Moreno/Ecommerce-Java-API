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
public class Produto {
	private int _idProduto;
	private String _nomeProduto;
	private String _nomeExtenso;
	private double _qualidadeProduto;
	private String _categoria;
	private String _statusProduto;
	private int _qtdEstoque;
	private double _preço;
	private Imagem _imagem;

	public Produto(int _idProduto, String _nomeProduto, String _nomeExtenso, double _qualidadeProduto, String _categoria, String _statusProduto, int _qtdEstoque, double _preço, Imagem imagem) {
		this._idProduto = _idProduto;
		this._nomeProduto = _nomeProduto;
		this._nomeExtenso = _nomeExtenso;
		this._qualidadeProduto = _qualidadeProduto;
		this._categoria = _categoria;
		this._statusProduto = _statusProduto;
		this._qtdEstoque = _qtdEstoque;
		this._preço = _preço;
		this._imagem = imagem;
	}
}
