package br.com.brazukas.controller;


import br.com.brazukas.DAO.PedidosDAO;

import br.com.brazukas.DAO.VendaDAO;
import br.com.brazukas.Models.*;
import br.com.brazukas.Models.Responses.PedidoResponse;
import br.com.brazukas.Models.Responses.PedidoResponseDto;

import br.com.brazukas.Models.Responses.ProdutoDetalhadoResponse;
import br.com.brazukas.Models.Responses.VendaHasProdutoJoinResponse;

import br.com.brazukas.controller.Dto.PedidoDto;

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
            List<Pedido> pedidos = PedidosDAO.getPedidos(idCliente);

        return ResponseEntity.ok(new PedidoResponse(200, pedidos.size() +" pedidos encontrados", pedidos));
    }

    @ApiOperation(value = "Lista pedidos detalhados")
    @RequestMapping(method = RequestMethod.GET, value = "/getPedidos/detalhado")
    public ResponseEntity<?> getPedidosDetalhado(int idVenda) throws IOException {

        var pedidos = PedidosDAO.getPedidosProdutos(idVenda);

        Payment pagamento =  VendaDAO.formaPagamento(idVenda);
        String dados = PedidosDAO.retonaIdEntregaECliente(idVenda);
        String [] dadosSplitado = dados.split(","); //posicao 0 igual idEntrega e posicao igual idCliente

        Endereco endereco = PedidosDAO.getEnderecoEntrega(Integer.parseInt(dadosSplitado[0]),Integer.parseInt(dadosSplitado[1]));


        return ResponseEntity.ok(new ProdutoDetalhadoResponse(200, pedidos.size()+" produtos encontrados", pedidos, pagamento,endereco));
    }
    
    @ApiOperation(value = "Atualiza o status do Pedido")
    @RequestMapping(method = RequestMethod.PUT,value = "/atualizaStatusPedido")
    public PedidoResponseDto atualizaStatusPedido(@RequestBody PedidoAtualizar pedido) throws IOException{

        boolean atualizou = PedidosDAO.atualizaStatusPedido(pedido.get_idVenda(), pedido.get_status());

        if(atualizou){
            return new PedidoResponseDto(200, "Atualizado com sucesso!", null);
        }else{
            return new PedidoResponseDto(500, "Erro para atualizar o status", null);
        }

    }
}
