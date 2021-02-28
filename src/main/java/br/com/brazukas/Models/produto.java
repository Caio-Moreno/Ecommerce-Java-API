package br.com.brazukas.Models;

import lombok.Data;

@Data
public class produto {
	private int idProduto;
	private String nomeProduto;
	private String nomeExtenso;
	private int qualidadeProduto;
	private String categoria;
	private char statusProduto;
	private int qtdEstoque;
	private double preço;

	
}
