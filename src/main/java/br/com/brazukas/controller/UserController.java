package br.com.brazukas.controller;

import br.com.brazukas.DAO.UserDAO;
import br.com.brazukas.Models.Responses.UserResponse;
import br.com.brazukas.Models.User;
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
            return new UserResponse(404, "Nenhum usuário encontrado aaaaa", null);
        }else{
            return new UserResponse(200, users.size()+" usuários encontrados", users);
        }
    }

    @ApiOperation("Verifica usuário e senha para login")
    @RequestMapping(method = RequestMethod.POST)
    public UserResponse getUsuario(@RequestBody UserDto userDto) throws  IOException{
        User user = UserDAO.VerificaLogin(userDto);
        if(user == null){
            return new UserResponse(500, "Usuário não encontrado", null);
        }else{
            List<User> lista = new ArrayList<>();
            lista.add(user);
            return new UserResponse(200, "Usuário encontrado", lista);
        }
    }

}
