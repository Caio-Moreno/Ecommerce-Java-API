package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.ProdutoAtualizar;
import br.com.brazukas.Models.ProdutoPlataforma;
import br.com.brazukas.Models.ProdutoStatus;
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
    public ProdutoResponseDto ListaTodosProdutos() throws IOException, SQLException {
        List<ProdutoDto> listaProd =ProdutoDAO.consultarProduto();
        return new ProdutoResponseDto(200,listaProd.size()+" Produtos encontrados", listaProd);
    }

    @ApiOperation(value = "Retorna uma lista de produtos geral")
    @RequestMapping(params = {"status"}, method = RequestMethod.GET)
    public ProdutoResponseDto ListaTodosProdutosGeral(String status) throws IOException, SQLException {
        List<ProdutoDto> listaProd =ProdutoDAO.consultarProdutoGeral();
        return new ProdutoResponseDto(200,listaProd.size()+" Produtos encontrados", listaProd);
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por nome")
    @RequestMapping(params = {"Nome"}, method = RequestMethod.GET)
    public ResponseEntity<ProdutoResponse> ListaProdutosPorNome(String Nome) throws IOException {
        List<Produto> listaProdutos =  ProdutoDAO.consultaProdutoPorNome(Nome);


        return ResponseEntity.ok().body(new ProdutoResponse(200, listaProdutos.size()+" Produtos encontrados", listaProdutos));
    }

    @ApiOperation(value = "Retorna uma lista de produtos filtros por ID")
    @RequestMapping(params = {"Id"}, method = RequestMethod.GET)
    public ResponseEntity<?> ListaProdutosPorid(int Id, @RequestHeader("TOKEN") String meuToken) throws IOException {
        System.out.println("MEU TOKEN"+meuToken);

        if(!TokenController.isValid(meuToken)) return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401,"Token inválido","Lista produtos por ID", "/Produtos{Id}"));

        Produto prod =  ProdutoDAO.consultaProdutoPorId(Id);

        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(prod);
        return  ResponseEntity.ok().body(new ProdutoResponse(200, listaProdutos.size()+" Produtos encontrados", listaProdutos));
    }

    @ApiOperation(value = "Insere um produto")
    @RequestMapping(method =  RequestMethod.POST)
    public ProdutoResponseDto InserirProduto(@RequestBody Produto produto, @RequestHeader("TOKEN") String meuToken) throws IOException, SQLException {
                System.out.println("INSERE PRODUTO()"+meuToken);
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
            public ProdutoResponse atualizaProduto(@RequestBody Produto produto, int Id ) throws IOException {
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
            public ProdutoResponse DeletaProduto(int Id) throws IOException {
                Produto prod = ProdutoDAO.consultaProdutoPorId(Id);

                if (prod == null) {
                    return new ProdutoResponse(404, "Produto não encontrado na base", null);
                }

                boolean deletou = ProdutoDAO.deletarProduto(Id);

                if(deletou){
                    return new ProdutoResponse(200, "Produto deletado com sucesso", null);
                }else{
                    return new ProdutoResponse(500, "Erro para deletar produto", null);
                }
            }

            @ApiOperation(value = "Atualiza o status do produto")
            @RequestMapping(method = RequestMethod.PUT)
            public ProdutoResponseDto atualizaStatus(@RequestBody ProdutoAtualizar prod) throws IOException{
                boolean atualizou = ProdutoDAO.atualizaStatus(prod.get_id(), prod.get_status());

                if(atualizou){
                    return new ProdutoResponseDto(200, "Atualizado com sucesso!", null);
                }else{
                    return new ProdutoResponseDto(500, "Erro para atualizar o status", null);
                }

            }

            @ApiOperation(value = "Get Status Produto")
            @RequestMapping(params = { "estado"}, method = RequestMethod.GET)
            public ProdutoStatusResponse consultas(String estado) throws IOException {
                    ProdutoStatus prod = ProdutoDAO.consultaStatus();
                    return new ProdutoStatusResponse(200, "Status encontrado", prod);
            }

            @ApiOperation(value = "Get Status Produto")
            @RequestMapping(params = { "plataforma"}, method = RequestMethod.GET)
            public ProdutoPlataformaResponse plataformas(String plataforma) throws IOException {
                ProdutoPlataforma prod = ProdutoDAO.consultaPlataforma();
                return new ProdutoPlataformaResponse(200, "Status encontrado", prod);
            }
}