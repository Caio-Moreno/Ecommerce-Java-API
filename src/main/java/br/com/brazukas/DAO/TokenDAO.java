package br.com.brazukas.DAO;

import br.com.brazukas.Util.ConexaoDb;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class TokenDAO {
    public static boolean verificarSeExiste(String token){
        System.out.println("verificaSeExiste()");
        String sql = "SELECT * FROM BRAZUKAS.TOKENS\n" +
                     "where TOKEN = ?;";

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,token);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean isValid(String token){
        String sql = "SELECT * FROM BRAZUKAS.TOKENS\n" +
                     "where 1=1\n" +
                     "and DATE_EXPIRED >= now()\n" +
                     "and TOKEN = ?;";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,token);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) return true;

            return false;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean autenticar(String token, int idLogin) throws IOException {
        System.out.println("Tokengerado----->"+token);
        if(token.isEmpty() || token == null) return false;
        System.out.println("Tokengerado----->"+token);
        String sqlInsert = "INSERT INTO TOKENS(ID, TOKEN, DATE_EXPIRED) VALUES(DEFAULT, ?, date_add(now(), interval 4 hour));";
        String sqlUpdate = "UPDATE LOGIN SET TOKEN = ? WHERE ID = ?;";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            PreparedStatement ps2 = con.prepareStatement(sqlUpdate);
            ps.setString(1, token);

            ps2.setString(1,token);
            ps2.setInt(2, idLogin);
            System.out.println("Tokengerado----->"+token);
            System.out.println("Tokengerado----->"+ps);
            System.out.println("Tokengerado----->"+ps2);
            //executo o insert
            ps.execute();

            //executo o update
            ps2.executeUpdate();

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean logout(String token, boolean valido, int idCliente){
            String updateToken = "UPDATE TOKENS\n" +
                    "SET DATE_EXPIRED = date_add(now(), interval -1 hour) \n" +
                    "WHERE TOKEN = ?;";
            String updateUser = "UPDATE LOGIN SET TOKEN = NULL WHERE ID = ?";
            try{
                Connection con = ConexaoDb.getConnection();
                PreparedStatement ps = con.prepareStatement(updateUser);
                ps.setInt(1,idCliente);
                System.out.println(ps);
                if (valido){
                    PreparedStatement ps2 = con.prepareStatement(updateToken);
                    ps2.setString(1,token);
                    System.out.println(ps2);
                    ps2.executeUpdate();
                }
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

    }
}
