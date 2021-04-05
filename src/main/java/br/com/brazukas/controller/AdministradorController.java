package br.com.brazukas.controller;

import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.DAO.UsuarioInternoDAO;
import br.com.brazukas.Models.*;
import br.com.brazukas.Models.Responses.*;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.controller.Dto.UsuarioInternoDto;
import com.sun.el.parser.Token;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static br.com.brazukas.Util.ConverteSenhaParaMd5.convertToMd5;


@CrossOrigin
@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @ApiOperation(value = "Retorna uma lista de estoquistas e administradores ativos. ")
    @RequestMapping(method = RequestMethod.GET,value = "/listarFuncionariosAtivos")
    public UserAdminOrEstoquistaResponse ListaTodosUsuariosInternosAtivos() throws IOException, SQLException {
        List<UsuarioInternoDto> listaUsuarios = UsuarioInternoDAO.listarUsuariosInternosAtivos();
        return new UserAdminOrEstoquistaResponse(200,listaUsuarios.size()+" Produtos encontrados", listaUsuarios);
    }

    @ApiOperation(value = "Retorna uma lista de estoquistas e administradores ativos e inativos. ")
    @RequestMapping(method = RequestMethod.GET,value = "/listarFuncionarios")
    public UserAdminOrEstoquistaResponse ListaTodosUsuariosInternos() throws IOException, SQLException {
        List<UsuarioInternoDto> listaUsuarios = UsuarioInternoDAO.listarUsuariosInterno();
        return new UserAdminOrEstoquistaResponse(200,listaUsuarios.size()+" Produtos encontrados", listaUsuarios);
    }
    
    @ApiOperation(value = "Retorna uma lista de usuarios filtros por ID")
    @RequestMapping(params = {"Id"}, method = RequestMethod.GET)
    public UserAlterarResponse ListaUsuariosPorId(int Id) throws IOException {
    	UserAlterar user =  UsuarioInternoDAO.consultaUsuarioPorId(Id);

        List<UserAlterar> listaFuncionarios = new ArrayList<>();
        listaFuncionarios.add(user);
        return new UserAlterarResponse(200, listaFuncionarios.size()+" Produtos encontrados", listaFuncionarios);
    }



    @ApiOperation(value = "Insere um cliente")
    @RequestMapping(method =  RequestMethod.POST)
    public UsuarioInternoInserirResponse InserirUserInterno(@RequestBody UsuarioInterno usuarioInterno) throws IOException, SQLException {

        if(usuarioInterno == null){
            return new UsuarioInternoInserirResponse(503, "Erro para processar a solicitação(Valor do usuário é null)", null);
        }
        usuarioInterno.set_password(convertToMd5(usuarioInterno.get_password()));

        usuarioInterno.set_cpf(usuarioInterno.get_cpf().replace(".", "").replace("-", ""));

        boolean inseriu = UsuarioInternoDAO.inserirUsuarioInterno(usuarioInterno);
        List<UsuarioInterno> list = new ArrayList<>();


        if(inseriu) {
            var ultimoUsuarioInterno =  UsuarioInternoDAO.retornarUltimoUsuarioInterno();
            list.add(ultimoUsuarioInterno);
            return  new UsuarioInternoInserirResponse(200,"Usuário inserido com sucesso",list);
        }else{

            return new UsuarioInternoInserirResponse(500,"Erro para inserir o produto",null);
        }
    }

    @ApiOperation(value = "Atualiza o usúario recebendo o parametro Id")
    @RequestMapping(params = {"Id"}, method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaUser(@RequestBody UserAlterar userAlterar, int Id , @RequestHeader("TOKEN") String token) throws IOException {

        if(!(TokenController.isValid(token))) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "atualiza usuario por id", "/administrador?Id={id}"));

        if(userAlterar == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserAlterarResponse(503, "Erro para processar a solicitação(Valor do usuário é null)", null));
        }
        userAlterar.set_password(convertToMd5(userAlterar.get_password()));
        boolean inseriu = UsuarioInternoDAO.atualizarUsuarioInterno(userAlterar, Id);
        List<UserAlterar> list = new ArrayList<>();
        if(inseriu){
            var user = UsuarioInternoDAO.consultaUsuarioPorId(Id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserAlterarResponse(404, "Usuario não encontrado na base", null));
            }
            list.add(user);
            return ResponseEntity.status(HttpStatus.OK).body(new UserAlterarResponse(200, "Usuario atualizado com sucesso", list));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserAlterarResponse(500, "Erro para atualizar o usuario", null));
        }
    }


    @ApiOperation(value = "Atualiza o status do usúario")
    @RequestMapping(method = RequestMethod.PUT,value = "/atualizaStatus")
    public UserAdminOrEstoquistaResponse atualizaStatus(@RequestBody UserAtualizar user) throws IOException{

        boolean atualizou = UsuarioInternoDAO.atualizaStatus(user.get_id(), user.get_status().getStatus());

        if(atualizou){
            return new UserAdminOrEstoquistaResponse(200, "Atualizado com sucesso!", null);
        }else{
            return new UserAdminOrEstoquistaResponse(500, "Erro para atualizar o status", null);
        }

    }


}