package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.ProdutoAtualizar;
import br.com.brazukas.Models.Responses.*;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Produto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProdutoResponseDto> ListaTodosProdutos() throws IOException, SQLException {
        List<ProdutoDto> listaProd = ProdutoDAO.consultarProduto();

        if (listaProd == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponseDto(404, "Nennhum produto encontrado", null));

        return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponseDto(200, listaProd.size() + " Produtos encontrados", listaProd));
    }

    @ApiOperation(value = "Retorna uma lista de produtos")
    @RequestMapping(method = RequestMethod.GET, value = "/Loja")
    public ResponseEntity<ProdutoResponseDto> ListaTodosProdutosLoja() throws IOException, SQLException {
        List<ProdutoDto> listaProd = ProdutoDAO.consultarProdutoLoja();

        if (listaProd == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponseDto(404, "Nennhum produto encontrado", null));

        return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponseDto(200, listaProd.size() + " Produtos encontrados", listaProd));
    }

    @ApiOperation(value = "Retorna uma lista de produtos geral")
    @RequestMapping(params = {"status"}, method = RequestMethod.GET)
    public ResponseEntity<ProdutoResponseDto> ListaTodosProdutosGeral(String status) throws IOException, SQLException {
        List<ProdutoDto> listaProd = ProdutoDAO.consultarProdutoGeral();

        if (listaProd == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponseDto(404, "Nennhum produto encontrado", null));

        return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponseDto(200, listaProd.size() + " Produtos encontrados", listaProd));
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por nome")
    @RequestMapping(params = {"Nome"}, method = RequestMethod.GET)
    public ResponseEntity<?> ListaProdutosPorNome(String Nome) throws IOException {
        List<Produto> listaProdutos = ProdutoDAO.consultaProdutoPorNome(Nome);

        if (listaProdutos == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponseDto(404, "Nennhum produto encontrado", null));

        return ResponseEntity.ok().body(new ProdutoResponse(200, listaProdutos.size() + " Produtos encontrados", listaProdutos));
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por ID")
    @RequestMapping(params = {"Id"}, method = RequestMethod.GET)
    public ResponseEntity<?> ListaProdutosPorid(int Id) throws IOException {
        Produto prod = ProdutoDAO.consultaProdutoPorId(Id);
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(prod);
        return ResponseEntity.ok().body(new ProdutoResponse(200, listaProdutos.size() + " Produtos encontrados", listaProdutos));
    }

    @ApiOperation(value = "Insere um produto")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> InserirProduto(@RequestBody Produto produto, @RequestHeader("TOKEN") String meuToken) throws IOException, SQLException {

        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista produtos por ID", "/Produtos{Id}"));

        boolean inseriu = ProdutoDAO.inserirProduto(produto);
        List<ProdutoDto> list = new ArrayList<>();


        if (inseriu) {
            ProdutoDto prod = ProdutoDAO.retornarUltimoProduto();
            list.add(prod);
            return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponseDto(200, "Produto inserido com sucesso", list));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProdutoResponseDto(500, "Erro para inserir o produto", null));
        }
    }

    @ApiOperation(value = "Atualiza o produto recebendo o parametro Id")
    @RequestMapping(params = {"Id"}, method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaProduto(@RequestBody Produto produto, int Id, @RequestHeader("TOKEN") String meuToken) throws IOException {

        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista produtos por ID", "/Produtos{Id}"));

        boolean inseriu = ProdutoDAO.atualizarProduto(produto, Id);
        List<Produto> list = new ArrayList<>();
        if (inseriu) {
            Produto prod = ProdutoDAO.consultaProdutoPorId(Id);
            if (prod == null) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponse(404, "Produto não encontrado na base", null));
            }
            list.add(prod);
            return  ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponse(200, "Produto atualizado com sucesso", list));
        } else {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProdutoResponse(500, "Erro para atualizar o produto", null));
        }
    }

    @ApiOperation(value = "Deleta o produto recebendo o parametro Id")
    @RequestMapping(params = {"Id"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> DeletaProduto(int Id,@RequestHeader("TOKEN") String meuToken) throws IOException {

        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista produtos por ID", "/Produtos{Id}"));

        Produto prod = ProdutoDAO.consultaProdutoPorId(Id);

        if (prod == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProdutoResponse(404, "Produto não encontrado na base", null));
        }

        boolean deletou = ProdutoDAO.deletarProduto(Id);

        if (deletou) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponse(200, "Produto deletado com sucesso", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProdutoResponse(500, "Erro para deletar produto", null));
        }
    }

    @ApiOperation(value = "Atualiza o status do produto")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaStatus(@RequestBody ProdutoAtualizar prod, @RequestHeader("TOKEN") String meuToken) throws IOException {

        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Atualiza status do produto", "/Produtos"));

        boolean atualizou = ProdutoDAO.atualizaStatus(prod.get_id(), prod.get_status());

        if (atualizou) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProdutoResponseDto(200, "Atualizado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProdutoResponseDto(500, "Erro para atualizar o status", null));
        }

    }
}