package br.com.brazukas.controller;


import br.com.brazukas.DAO.PedidosDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.ProdutoAtualizar;
import br.com.brazukas.Models.Responses.PedidoResponse;
import br.com.brazukas.Models.Responses.PedidoResponseDto;
import br.com.brazukas.Models.Responses.ProdutoResponseDto;
import br.com.brazukas.Models.Responses.TokenResponse;
import br.com.brazukas.Models.Responses.VendaHasProdutoJoinResponse;
import br.com.brazukas.controller.Dto.PedidoDto;
import br.com.brazukas.controller.Dto.ProdutoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Pedidos")
public class PedidosController {

	@ApiOperation(value = "Retorna uma lista de pedidos")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PedidoResponseDto> con() throws IOException, SQLException {
        List<PedidoDto> listaPedido = PedidosDAO.consultarPedidoGeral();

        if (listaPedido == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PedidoResponseDto(404, "Nennhum Pedido encontrado", null));

        return ResponseEntity.status(HttpStatus.OK).body(new PedidoResponseDto(200, listaPedido.size() + " Pedidos encontrados", listaPedido));
    }
	
    @ApiOperation(value = "Lista pedidos")
    @RequestMapping(method = RequestMethod.GET, value = "/getPedidos")
    public ResponseEntity<?> getPedidos(int idCliente) throws IOException {

        var pedidos = PedidosDAO.getPedidos(idCliente);

        return ResponseEntity.ok(new PedidoResponse(200, pedidos.size()+" pedidos encontrados", pedidos));
    }

    @ApiOperation(value = "Lista pedidos detalhados")
    @RequestMapping(method = RequestMethod.GET, value = "/getPedidos/detalhado")
    public ResponseEntity<?> getPedidosDetalhado(int idVenda) throws IOException {

        var pedidos = PedidosDAO.getPedidosProdutos(idVenda);

        return ResponseEntity.ok(new VendaHasProdutoJoinResponse(200, pedidos.size()+" produtos encontrados", pedidos));
    }
}
