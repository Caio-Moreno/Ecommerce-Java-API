package br.com.brazukas.controller;

import br.com.brazukas.DAO.TokenDAO;

import java.io.IOException;
import java.util.UUID;

public class TokenController {

    public static String autenticar(int idLogin) throws IOException {
        String token = gerarToken();

        if(TokenDAO.autenticar(token, idLogin)) return token; //caso autentique

        return null;
    }

    public static boolean logout(String token, int idCliente){
        System.out.println("logout()");
      return TokenDAO.logout(token, isValid(token), idCliente);
    }

    public static boolean isValid(String token){
        System.out.println("isvalid()");
        return TokenDAO.verificarSeExiste(token) && TokenDAO.isValid(token);
    }

    private static String gerarToken(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","").substring(0,20);
    }
}
