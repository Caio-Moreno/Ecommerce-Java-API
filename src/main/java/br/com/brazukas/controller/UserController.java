package br.com.brazukas.controller;

import br.com.brazukas.DAO.UserDAO;
import br.com.brazukas.Models.Responses.UserResponse;
import br.com.brazukas.Models.User;
import br.com.brazukas.Models.UserCadastro;
import br.com.brazukas.controller.Dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

@CrossOrigin
@RestController
@RequestMapping("/Users")
public class UserController {

    @ApiOperation("Lista todos os usuários")
    @RequestMapping(method = RequestMethod.GET)
    public UserResponse getUsuarios() throws IOException {
        System.out.println("ENTREI");
        List<User> users = UserDAO.consultaUsuarios();
        if(users == null){
            return new UserResponse(404, "Nenhum usuário encontrado", null,null);
        }else{
            return new UserResponse(200, users.size()+" usuários encontrados", users,null);
        }
    }

    @ApiOperation("Verifica usuário e senha para login")
    @RequestMapping(method = RequestMethod.POST)
    public UserResponse login(@RequestBody UserDto userDto) throws  IOException{
        User user = UserDAO.VerificaLogin(userDto);

        if(user == null){
            return new UserResponse(403, "Usuário/senha incorretos", null, null);
        }else{
            String uid = User.gerarUid();
            boolean autenticou = UserDAO.autenticar(uid,user.get_id());
            if(autenticou) {
                List<User> lista = new ArrayList<>();
                lista.add(user);
                return new UserResponse(200, "Usuário autenticado", lista, uid);
            }else{
                return new UserResponse(403, "Erro na autenticação", null,null);
            }
        }
    }

    @ApiOperation("Cria usuário(Básico)")
    @RequestMapping(value = "/criar", method = RequestMethod.POST)
    public UserResponse criarLogin(@RequestBody UserCadastro userCadastro) throws  IOException{
        if(userCadastro == null){
            return new UserResponse(503, "Erro para processar a solicitação(Valor do usuário é null)", null,null);
        }
        User user = UserDAO.criarUsuarioBasico(userCadastro);

        if(user == null){
            return new UserResponse(503, "Erro para criar o usuário", null, null);
        }else{
            List<User> lista = new ArrayList<>();
            lista.add(user);
            return new UserResponse(200, "Usuário criado com sucesso", lista, null);
        }

    }

}
