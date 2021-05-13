package br.com.brazukas.controller;


import br.com.brazukas.DAO.PedidosDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.ProdutoAtualizar;
import br.com.brazukas.Models.Responses.PedidoResponse;
import br.com.brazukas.Models.Responses.ProdutoResponseDto;
import br.com.brazukas.Models.Responses.TokenResponse;
import br.com.brazukas.Models.Responses.VendaHasProdutoJoinResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/Pedidos")
public class PedidosController {

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
