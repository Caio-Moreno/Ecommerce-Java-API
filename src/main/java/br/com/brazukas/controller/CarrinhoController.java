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
    @ApiOperation(value = "Retorna insere no carrinho")
    @RequestMapping(method = RequestMethod.POST)
    public CarrinhoResponse enviarParaCarrinho(@RequestBody Carrinho carrinho) throws IOException {
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

    @ApiOperation(value = "Retorna o carrinho")
    @RequestMapping(params = {"id"},method = RequestMethod.GET)
    public CarrinhoResponse consultaCarrinho(int id) throws IOException {
        boolean existeCliente = ClienteDAO.clienteExite(id);
        if(existeCliente) {
            List<Carrinho> lista = CarrinhoDAO.Consultar(id);
            if(lista != null){
                return  new CarrinhoResponse(200,lista.size(),"Produtos encontrados no carrinho",lista);
            }else {
                return  new CarrinhoResponse(500,0,"Nenhum item no carrinho",null);
            }
        }
        return  new CarrinhoResponse(404,0,"Ainda não existe nenhum carrinho de compra vinculado ao cliente",null);
    }



}
