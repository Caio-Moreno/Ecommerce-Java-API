package br.com.brazukas.Models.Responses;

import br.com.brazukas.Models.ProdutoPlataforma;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoPlataformaResponse {
    private int _status;
    private String _message;
    private ProdutoPlataforma prod;
}
