package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<ProdutoDto> ListaProdutos() throws IOException, SQLException {

        gravaLog("nome->>"+nome, "padrao", Level.INFO);
        List<ProdutoDto> lista = ProdutoDAO.consultarProduto();

        return  lista;
    }

    @RequestMapping(params = {"Nome"}, method = RequestMethod.GET)
    public List<Produto> ListaProdutoPorNome(String Nome) throws IOException, SQLException {
        List<Produto> lista = ProdutoDAO.consultaProdutoPorNome(Nome);
        return  lista;
    }
    
    @RequestMapping(method =  RequestMethod.POST)
    public String Post(@RequestBody Produto produto) throws IOException, SQLException {

        boolean inseriu = ProdutoDAO.inserirProduto(produto);

        if(inseriu) {
            return "Produto inserido com sucesso";
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro para inserir produto");
        }

    }




}
