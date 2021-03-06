package br.com.brazukas.Models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaHasProduto {
    Venda venda;
    List<Produto> produtos;
}
