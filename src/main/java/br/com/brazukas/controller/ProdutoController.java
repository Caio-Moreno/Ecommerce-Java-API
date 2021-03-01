package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.Produto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

@RestController
public class ProdutoController {

    @RequestMapping(path = "/Produtos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> ListaProdutos() throws IOException, SQLException {

        return ProdutoDAO.consultarProduto();

    }

}
