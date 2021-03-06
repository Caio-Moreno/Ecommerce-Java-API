package br.com.brazukas.controller;

import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.Cliente;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.controller.infra.FileServer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;


@CrossOrigin
@RestController
@RequestMapping("/Produtos")
public class ProdutoController {

    private String nome;

    @Autowired
    private FileServer fileSaver;

    @ApiOperation(value = "Retorna uma lista de produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<ProdutoDto> ListaTodosProdutos() throws IOException, SQLException {

        gravaLog("nome->>"+nome, "padrao", Level.INFO);
        List<ProdutoDto> lista = ProdutoDAO.consultarProduto();

        return  lista;
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por nome")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de produtos filtrados por nome"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @RequestMapping(params = {"Nome"}, method = RequestMethod.GET)
    public List<Produto> ListaProdutosPorNome(String Nome) throws IOException, SQLException {
        List<Produto> lista = ProdutoDAO.consultaProdutoPorNome(Nome);
        return  lista;
    }

    @ApiOperation(value = "Insere um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto inserido"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @RequestMapping(method =  RequestMethod.POST)
    public ProdutoDto InserirProduto(@RequestBody Produto produto, List<MultipartFile> fotos) throws IOException, SQLException {
        boolean inseriu = ProdutoDAO.inserirProduto(produto);
        if(inseriu) {
            return ProdutoDAO.retornarUltimoProduto();
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro para inserir produto");
        }
    }

    @ApiOperation(value = "Atualiza o produto recebendo o parametro Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto atualizado"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @RequestMapping(params = {"Id"}, method = RequestMethod.PUT)
    public Produto atualizaCliente(@RequestBody Produto produto, int Id ) throws IOException, SQLException {

        boolean inseriu = ProdutoDAO.atualizarProduto(produto, Id);
        if(inseriu){
            return ProdutoDAO.consultaProdutoPorId(Id);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro para atualizar produto");
        }
    }
    @ApiOperation(value = "Deleta o produto recebendo o parametro Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a confirmação do produto"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @RequestMapping(params = {"Id"}, method = RequestMethod.DELETE)
    public String DeletaCliente(int Id) throws IOException, SQLException {
        Produto prod = ProdutoDAO.consultaProdutoPorId(Id);

        if (prod == null) {
            return "Erro para deletar produto, o mesmo não existe mais";
        }

        boolean deletou = ProdutoDAO.deletarCliente(Id);

        if(deletou){
            return "Produto deletado com sucesso!";
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro para deletar produto");
        }
    }
}