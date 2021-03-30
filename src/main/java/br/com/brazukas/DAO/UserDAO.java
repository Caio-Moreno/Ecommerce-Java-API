package br.com.brazukas.DAO;

import br.com.brazukas.Models.*;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.controller.Dto.UserDto;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

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
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                int id_cliente_fk = rs.getInt("ID_CLIENTE_FK");
                users.add(new User(id,login,password,permission, id_cliente_fk));
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
        System.out.println(sql);
        User user = null;
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,userDto.get_username());
            ps.setString(2,userDto.get_password());
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("ID");
                String login = rs.getString("LOGIN");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSAO");
                int id_cliente_fk = rs.getInt("ID_CLIENTE_FK");
                user = new User(id,login,password,permission, id_cliente_fk);
            }
        }catch (Exception e){
            System.out.println("erro"+e.getMessage());
            user = null;
        }
        return user;
    }

    public static boolean autenticar(String authenticationSid, int idLogin) throws IOException {
        if(authenticationSid.isEmpty() || authenticationSid == null) return false;

        String sqlUpdate = "UPDATE LOGIN SET AUTHENTICATION_SID = ? WHERE ID = ?;";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);
            ps.setString(1, authenticationSid);
            ps.setInt(2,idLogin);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            gravaLog("Erro"+e.getMessage(), "UserDAO", Level.SEVERE);
            return false;
        }
    }

    public  static User criarUsuarioBasico(UserCadastro userCadastro){
        User user = null;
        String sqlInsertCliente = "INSERT INTO CLIENTE(ID, NOME, TELEFONE, EMAIL) VALUES(DEFAULT, ?, ?,?);";
        String loginInsert = "INSERT INTO LOGIN(ID, LOGIN, PASSWORD, PERMISSAO, ID_CLIENTE_FK) VALUES(DEFAULT, ?,?,?,?)";
        int idCliente = 0;

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsertCliente, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,userCadastro.get_name());
            ps.setString(2,userCadastro.get_phone());
            ps.setString(3,userCadastro.get_email());
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
            PreparedStatement ps = con.prepareStatement(loginInsert);
            ps.setString(1,userCadastro.get_login());
            ps.setString(2,userCadastro.get_password());
            ps.setString(3,userCadastro.get_permission());
            ps.setInt(4,idCliente);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro para inserir user"+e.getMessage());
            user = null;
        }

        String sql = "SELECT * FROM LOGIN WHERE ID_CLIENTE_FK = ?";
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
                int id_cliente_fk = rs.getInt("ID_CLIENTE_FK");
                user = new User(id,login,password,permission, id_cliente_fk);
            }
        }catch (Exception e){
            System.out.println("erro"+e.getMessage());
            user = null;
        }
        return user;
    }
}
