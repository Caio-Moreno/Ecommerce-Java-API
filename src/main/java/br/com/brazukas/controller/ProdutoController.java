package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.Produto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

@RestController
@RequestMapping("/Produtos")
public class ProdutoController {

    private String nome;

    @RequestMapping(method = RequestMethod.GET)
    public List<Produto> ListaProdutos() throws IOException, SQLException {

        gravaLog("nome->>"+nome, "padrao", Level.INFO);
        List<Produto> lista = ProdutoDAO.consultarProduto();

        return  lista;
    }

    @RequestMapping(params = {"Nome"})
    public List<Produto> ListaProdutoPorNome(String Nome) throws IOException, SQLException {
        //gravaLog("Nome-->"+Nome+"Categoria-->"+Categoria,"PRINCIPALLLLL", Level.SEVERE);

        List<Produto> lista = ProdutoDAO.consultaProdutoPorNome(Nome);
        return  lista;
    }

}
