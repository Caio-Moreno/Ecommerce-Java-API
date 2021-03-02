package br.com.brazukas.controller.Dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoDto {


    private int _idProduto;
    private String _nomeProduto;
    private String _descricao;
    private double _qualidadeProduto;
    private String _categoria;
    private String _statusProduto;
    private int _qtdEstoque;
    private double _preço;
    private String _imagem;
    private String _plataforma;

}
