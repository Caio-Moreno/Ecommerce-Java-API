package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.Responses.ProdutoResponse;
import br.com.brazukas.Models.Responses.ProdutoResponseDto;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Produto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/Produtos")
public class ProdutoController {

    @ApiOperation(value = "Retorna uma lista de produtos")
    @RequestMapping(method = RequestMethod.GET)
    public ProdutoResponseDto ListaTodosProdutos() throws IOException, SQLException {
        List<ProdutoDto> listaProd =ProdutoDAO.consultarProduto();
        return new ProdutoResponseDto(200,listaProd.size()+" Produtos encontrados", listaProd);
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por nome")
    @RequestMapping(params = {"Nome"}, method = RequestMethod.GET)
    public ProdutoResponse ListaProdutosPorNome(String Nome) throws IOException {
        List<Produto> listaProdutos =  ProdutoDAO.consultaProdutoPorNome(Nome);


        return new ProdutoResponse(200, listaProdutos.size()+" Produtos encontrados", listaProdutos);
    }

    @ApiOperation(value = "Insere um produto")
    @RequestMapping(method =  RequestMethod.POST)
    public ProdutoResponseDto InserirProduto(@RequestBody Produto produto) throws IOException, SQLException {
        boolean inseriu = ProdutoDAO.inserirProduto(produto);
        List<ProdutoDto> list = new ArrayList<>();

        if(inseriu) {
            ProdutoDto prod =  ProdutoDAO.retornarUltimoProduto();
            list.add(prod);
            return  new ProdutoResponseDto(200,"Produto inserido com sucesso",list);
        }else{

            return new ProdutoResponseDto(500,"Erro para inserir o produto",null);
        }
    }

    @ApiOperation(value = "Atualiza o produto recebendo o parametro Id")
    @RequestMapping(params = {"Id"}, method = RequestMethod.PUT)
    public ProdutoResponse atualizaCliente(@RequestBody Produto produto, int Id ) throws IOException {
        boolean inseriu = ProdutoDAO.atualizarProduto(produto, Id);
        List<Produto> list = new ArrayList<>();
        if(inseriu){
            Produto prod = ProdutoDAO.consultaProdutoPorId(Id);
            if (prod == null) {
                return new ProdutoResponse(404, "Produto não encontrado na base", null);
            }
            list.add(prod);
            return new ProdutoResponse(200, "Produto atualizado com sucesso", list);
        }else{
            return new ProdutoResponse(500, "Erro para atualizar o produto", null);
        }
    }
    @ApiOperation(value = "Deleta o produto recebendo o parametro Id")
    @RequestMapping(params = {"Id"}, method = RequestMethod.DELETE)
    public ProdutoResponse DeletaCliente(int Id) throws IOException {
        Produto prod = ProdutoDAO.consultaProdutoPorId(Id);

        if (prod == null) {
            return new ProdutoResponse(404, "Produto não encontrado na base", null);
        }

        boolean deletou = ProdutoDAO.deletarCliente(Id);

        if(deletou){
            return new ProdutoResponse(200, "Produto deletado com sucesso", null);
        }else{
            return new ProdutoResponse(500, "Erro para deletar produto", null);
        }
    }
}