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
	@Getter @Setter private String _nomeProduto;
	@Getter @Setter private String _descricao;
	@Getter @Setter private double _qualidadeProduto;
	@Getter @Setter private String _categoria;
	@Getter @Setter private String _statusProduto;
	@Getter @Setter private int _qtdEstoque;
	@Getter @Setter private double _preco;
	@Getter @Setter private Imagem _imagem;
	@Getter @Setter private String _plataforma;
}
