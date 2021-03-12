package br.com.brazukas.DAO;

import br.com.brazukas.Models.*;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.controller.Dto.UserDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> consultaUsuarios() throws IOException {
        String sql = "SELECT * FROM LOGIN";
        System.out.println(sql);
        List<User> users = new ArrayList<>();
        System.out.println(sql);
        try {

            Connection con = ConexaoDb.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("NOME");
                String surname = rs.getString("SOBRENOME");
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");

                users.add(new User(id,name,surname,login,password,permission));
            }
            for (User u: users) {
                System.out.println("-----"+u);
            }

        }catch (Exception e ){
            e.printStackTrace();
            users = null;
        }
        return users;
    }

    public static User VerificaLogin(UserDto userDto) {
        String sql = "SELECT * FROM LOGIN WHERE LOGIN = ? AND PASSWORD = ?";
        User user = null;
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,userDto.get_user());
            ps.setString(2,userDto.get_password());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("NOME");
                String surname = rs.getString("SOBRENOME");
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                user = new User(id,name,surname,login,password,permission);
            }
        }catch (Exception e){
            user = null;
        }
        return user;
    }
}
