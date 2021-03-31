package br.com.brazukas.controller;

import br.com.brazukas.DAO.LoginDAO;
import br.com.brazukas.Models.Login;
import br.com.brazukas.Models.Responses.LoginResponse;
import br.com.brazukas.Models.Responses.LogoutResponse;
import br.com.brazukas.controller.Dto.LoginDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Login")
public class LoginController {
    @ApiOperation("Verifica usuário e senha para login")
    @RequestMapping(method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginDto loginDto) throws IOException {
        Login login = LoginDAO.VerificaLogin(loginDto);
        List<Login> lista = new ArrayList<>();

        if(login == null){
            return new LoginResponse(403, "Usuário/senha incorretos", null);
        }else{

            if(TokenController.isValid(login.get_token())){
                lista.add(login);
                return new LoginResponse(200, "Usuário autenticado - chave já estava valida", lista);
            }else{
                System.out.println(login.get_token());
                login.set_token(TokenController.autenticar(login.get_id()));
                System.out.println(login.get_token());
                lista.add(login);
                return new LoginResponse(200, "Usuário autenticado", lista);
            }
        }
    }
    @ApiOperation("Verifica usuário e senha para login")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public LogoutResponse logout(@RequestBody Login login) throws  IOException{
        System.out.println(login);
        System.out.println(login.get_token());
        if(TokenController.logout(login.get_token(), login.get_id())) {
            return new LogoutResponse(200, "Deslogado com sucesso");
        } else{
            return new LogoutResponse(503, "Erro para deslogar");
        }
    }
}
