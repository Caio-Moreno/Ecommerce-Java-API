package br.com.brazukas.controller;


import br.com.brazukas.DAO.CarrinhoDAO;
import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.DAO.VendaDAO;
import br.com.brazukas.Models.Carrinho;
import br.com.brazukas.Models.Responses.CarrinhoResponse;
import br.com.brazukas.Models.Responses.VendaResponse;
import br.com.brazukas.Models.Venda;
import br.com.brazukas.Util.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Carrinho")
public class CarrinhoController {

    @ApiOperation(value = "Insere no carrinho porém se o produto já existe no carrinho ele só atualiza")
    @RequestMapping(method = RequestMethod.POST)
    public CarrinhoResponse enviarParaCarrinho(@RequestBody Carrinho carrinho) throws IOException {
        boolean existeCliente = ClienteDAO.clienteExite(carrinho.get_idCliente());
        boolean existeProduto = ProdutoDAO.existeProduto(carrinho.get_idProduto());
        boolean existeProdutoNoCarrinho = CarrinhoDAO.existeProduto(carrinho);
        System.out.println("EXISTE-->"+existeProdutoNoCarrinho);
        boolean continuar = (existeCliente && existeProduto);
        if(continuar) {

            boolean inseriu = existeProdutoNoCarrinho ? CarrinhoDAO.atualizar(carrinho) : CarrinhoDAO.inserir(carrinho);
            if (inseriu) {
                List<Carrinho> lista = CarrinhoDAO.Consultar(carrinho.get_idCliente());
                return  new CarrinhoResponse(200,1,"Produto inserido ou atualizado",lista);
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


    @ApiOperation(value = "Decrementa quantidade")
    @RequestMapping(params = {"id", "idCliente"}, method = RequestMethod.POST, value="/decrement/logged")
    public CarrinhoResponse decrementaCarrinhoLogado(int id, int idCliente) throws IOException {
        Utils.printarNaTela("Meu id-->>"+id+" minha sessao-->>"+idCliente);
        boolean deletou = CarrinhoDAO.decrementaProdutoLogado(id,idCliente);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Ok", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para decrementar", null);
        }
    }

    @ApiOperation(value = "incrementa quantidade logado")
    @RequestMapping(params = {"id", "idCliente"}, method = RequestMethod.POST, value="/increment/logged")
    public CarrinhoResponse incrementaCarrinhoLogado(int id, int idCliente) throws IOException {
        Utils.printarNaTela("Meu id-->>"+id+" minha sessao-->>"+idCliente);
        boolean deletou = CarrinhoDAO.incrementaProdutoLogado(id,idCliente);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Ok", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para decrementar", null);
        }
    }


    @ApiOperation(value = "Insere no carrinho com o cliente deslogado")
    @RequestMapping(method = RequestMethod.POST, value = "/Deslogado")
    public CarrinhoResponse enviarParaCarrinhoClienteDeslogado(@RequestBody Carrinho carrinho) throws IOException {
        Utils.printarNaTela("Sessao"+carrinho.get_sessionId());
        boolean existeProdutoNoCarrinho = CarrinhoDAO.existeProduto(carrinho);
        Utils.printarNaTela("aqui"+existeProdutoNoCarrinho);

        boolean inseriu = existeProdutoNoCarrinho ? CarrinhoDAO.atualizar(carrinho) : CarrinhoDAO.inserir(carrinho);

        if (inseriu) {
            return  new CarrinhoResponse(200,1,"Produto inserido ou atualizado",null);
        }else{
            return  new CarrinhoResponse(500,0,"Erro para inserir o produto no carrinho",null);
        }
    }

    @ApiOperation(value = "Retorna o carrinho com o cliente deslogado")
    @RequestMapping(params = {"session"},method = RequestMethod.GET, value = "/getCart")
    public CarrinhoResponse consultaCarrinhoDeslogado(String session) throws IOException {
        Utils.printarNaTela("RETORNA CARRINHO");
        boolean existeCliente = ClienteDAO.existeSessao(session);
        Utils.printarNaTela("Passei da sessão");



        if(existeCliente) {
            List<Carrinho> lista = CarrinhoDAO.ConsultarDeslogado(session);

            CarrinhoDAO.deletaProdutoZerado(lista, session);

            if(lista != null){
                return  new CarrinhoResponse(200,lista.size(),"Produtos encontrados no carrinho",lista);
            }else {
                return  new CarrinhoResponse(500,0,"Nenhum item no carrinho",null);
            }
        }
        return  new CarrinhoResponse(404,0,"Ainda não existe nenhum carrinho de compra vinculado ao cliente",null);
    }

    @ApiOperation(value = "Deleta produto do carrinho")
    @RequestMapping(params = {"id", "session"}, method = RequestMethod.DELETE, value="/deleteCart")
    public CarrinhoResponse deletaDoCarrinho(int id, String session) throws IOException {
        Utils.printarNaTela("Meu id-->>"+id+" minha sessao-->>"+session);
        boolean deletou = CarrinhoDAO.deletaProduto(id,session);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Produto deletado com sucesso", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para deletar o produto", null);
        }
    }


    @ApiOperation(value = "Decrementa quantidade")
    @RequestMapping(params = {"id", "session"}, method = RequestMethod.POST, value="/decrement")
    public CarrinhoResponse decrementaCarrinho(int id, String session) throws IOException {
        Utils.printarNaTela("Meu id-->>"+id+" minha sessao-->>"+session);
        boolean deletou = CarrinhoDAO.decrementaProduto(id,session);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Ok", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para decrementar", null);
        }
    }

    @ApiOperation(value = "incrementa quantidade")
    @RequestMapping(params = {"id", "session"}, method = RequestMethod.POST, value="/increment")
    public CarrinhoResponse incrementaCarrinho(int id, String session) throws IOException {
        Utils.printarNaTela("Meu id-->>"+id+" minha sessao-->>"+session);
        boolean deletou = CarrinhoDAO.incrementaProduto(id,session);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Ok", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para decrementar", null);
        }
    }

    @ApiOperation(value = "Atualiza Carrinho vinculando ele ao cliente")
    @RequestMapping(params = {"session", "idCliente"}, method = RequestMethod.PUT, value="/updateCart")
    public CarrinhoResponse vinculaCarrinho(String session, int idCliente) throws IOException {
        Utils.printarNaTela("Meu id-->>"+idCliente+ "sessao -->>"+session);
        boolean deletou = CarrinhoDAO.vinculaCarrinho(session, idCliente);

        if(deletou){
            return  new CarrinhoResponse(200, 0,"Ok", null);
        }else{
            return  new CarrinhoResponse(404, 0,"Erro para decrementar", null);
        }
    }
}
