package br.com.brazukas.Models;

import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produto {
	private int _idProduto;
	private String _nomeProduto;
	private String _descricao;
	private double _qualidadeProduto;
	private String _categoria;
	private String _statusProduto;
	private int _qtdEstoque;
	private double _preco;
	private Imagem _imagem;
	private String _plataforma;
}
