package br.com.brazukas.Models;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoStatus {
    private int _ativo;
    private int _inativo;
}
