package br.com.brazukas.Models.Responses;

import br.com.brazukas.controller.Dto.PedidoDto;
import br.com.brazukas.controller.Dto.ProdutoDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class  PedidoResponseDto {
    private int _response;
    private String _message;
    private List<PedidoDto> _pedido;
}
