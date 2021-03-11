package br.com.brazukas.Models.Responses;


import br.com.brazukas.Models.ProdutoStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdutoStatusResponse {
    private int _response;
    private String _message;
    private ProdutoStatus _status;
}
