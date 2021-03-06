package br.com.brazukas.controller;


import br.com.brazukas.DAO.CarrinhoDAO;
import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.DAO.VendaDAO;
import br.com.brazukas.Models.Carrinho;
import br.com.brazukas.Models.Responses.CarrinhoResponse;
import br.com.brazukas.Models.Responses.VendaResponse;
import br.com.brazukas.Models.Venda;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Carrinho")
public class CarrinhoController {
    @ApiOperation(value = "Retorna carrinho")
    @RequestMapping(method = RequestMethod.POST)
    public CarrinhoResponse enviarParaCarrinho(@RequestBody Carrinho carrinho) throws IOException {
        System.out.println(carrinho);
        boolean existeCliente = ClienteDAO.clienteExite(carrinho.get_idCliente());
        boolean existeProduto = ProdutoDAO.existeProduto(carrinho.get_idProduto());
        boolean continuar = (existeCliente && existeProduto);
        if(continuar) {
            boolean inseriu = CarrinhoDAO.inserir(carrinho);
            if (inseriu) {
                return  new CarrinhoResponse(200,1,"Produto inserido",null);
            }else{
                return  new CarrinhoResponse(500,0,"Erro para inserir o produto no carrinho",null);
            }
        }
        return  new CarrinhoResponse(404,0,"Produto não está mais disponivel para compra",null);
    }
}
