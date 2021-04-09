package br.com.brazukas.DAO;

import br.com.brazukas.Models.*;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> consultaUsuarios() throws IOException {
        String sql = "SELECT ID, EMAIL as 'LOGIN',PASSWORD, PERMISSAO, NOME FROM USUARIO";
        System.out.println(sql);
        List<User> users = new ArrayList<>();
        System.out.println(sql);
        try {

            Connection con = ConexaoDb.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("ID");
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                String nome = rs.getString("NOME");
                users.add(new User(id,login,password,permission,nome));
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

    public  static User criarUsuarioBasico(UserCadastro userCadastro){
        User user = null;
        String sqlInsertCliente = "insert into USUARIO(ID,NOME,EMAIL, PASSWORD, PERMISSAO, STATUS) VALUES(DEFAULT, ?, ?, ?, ?, 'A');";
        String telefoneInsert = "insert into CLIENTE_TELEFONE(ID_CLIENTE_FK, TELEFONE) VALUES(?,?);";
        int idCliente = 0;

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsertCliente, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,userCadastro.get_name());
            ps.setString(2,userCadastro.get_email());
            ps.setString(3,userCadastro.get_password());
            ps.setString(4,userCadastro.get_permission());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                idCliente = rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("Erro para inserir"+e.getMessage());
            user = null;
        }


        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(telefoneInsert);
            ps.setInt(1,idCliente);
            ps.setString(2,userCadastro.get_phone());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro para inserir user"+e.getMessage());
            user = null;
        }

        String sql = "SELECT ID, EMAIL as 'LOGIN',PASSWORD, PERMISSAO,NOME FROM USUARIO WHERE ID = ?;";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idCliente);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("ID");
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                String nome = rs.getString("NOME");
                user = new User(id,login,password,permission,nome);
            }
        }catch (Exception e){
            System.out.println("erro"+e.getMessage());
            user = null;
        }
        return user;
    }
}
