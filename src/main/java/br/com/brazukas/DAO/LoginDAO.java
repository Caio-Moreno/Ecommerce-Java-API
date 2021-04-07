package br.com.brazukas.DAO;

import br.com.brazukas.Models.Login;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.controller.Dto.LoginDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
    public static Login VerificaLogin(LoginDto loginDto) {
        System.out.println(loginDto);
        String sql = "SELECT ID, EMAIL as 'LOGIN',PASSWORD, PERMISSAO, TOKEN FROM USUARIO WHERE EMAIL = ? AND PASSWORD = ? AND STATUS = 'A'";
        Login login = null;
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,loginDto.get_username());
            ps.setString(2,loginDto.get_password());
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("ID");
                String loginResponse = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                String token = rs.getString("TOKEN");
                login = new Login(id, loginResponse,password,permission,token);
            }
        }catch (Exception e){
            System.out.println("erro"+e.getMessage());
            login = null;
        }
        return login;
    }
}
