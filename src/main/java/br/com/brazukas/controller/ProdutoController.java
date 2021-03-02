package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Produto;
import org.springframework.web.bind.annotation.*;

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
    public String Post(@RequestBody Produto produto)
    {

        
        return "$produtoNome="+produto.get_nomeProduto()+"$produtoPreco"+produto.get_preco();
    }




}
