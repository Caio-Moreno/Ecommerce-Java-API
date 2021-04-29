package br.com.brazukas.DAO;

import br.com.brazukas.Models.Carrinho;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.Utils;
import jdk.jshell.execution.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class CarrinhoDAO {

    public static boolean inserir(Carrinho carrinho) throws IOException {
        boolean inseriu = true;
        String sql = "INSERT INTO CARRINHO(ID_CLIENTE, ID_PRODUTO, STATUS, QUANTIDADE, VALOR, TOKEN_SESSION) VALUES(?,?,?,?,?,?);";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, carrinho.get_idCliente());
            ps.setInt(2, carrinho.get_idProduto());
            ps.setString(3, carrinho.get_status());
            ps.setInt(4, carrinho.get_quantidade());
            ps.setDouble(5, carrinho.get_valor());
            ps.setString(6, carrinho.get_sessionId());
            Utils.printarMinhaConsulta(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            Utils.printarErro("EXCEPTION" + e.getMessage());
            inseriu = false;
        }
        return inseriu;
    }

    public static List<Carrinho> Consultar(int id) throws IOException {
        List<Carrinho> lista = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE,ID_PRODUTO, STATUS, SUM(QUANTIDADE) as QUANTIDADE, SUM(VALOR *QUANTIDADE) AS VALOR FROM CARRINHO WHERE ID_CLIENTE = ? GROUP BY ID_CLIENTE,ID_PRODUTO,STATUS, QUANTIDADE;";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("ID_CLIENTE");
                int idProduto = rs.getInt("ID_PRODUTO");
                String status = rs.getString("STATUS");
                int quantidade = rs.getInt("QUANTIDADE");
                double valor = rs.getDouble("VALOR");
                String sessionId = rs.getString("TOKEN_SESSION");
                lista.add(new Carrinho(idCliente, idProduto, status, quantidade, valor, sessionId,""));
            }
        } catch (Exception e) {
            gravaLog("ERRO NA BUSCA" + e.getMessage(), "CARRINHODAO", Level.SEVERE);
            lista = null;
        }
        return lista;
    }

    public static List<Carrinho> ConsultarDeslogado(String session) throws IOException {
        List<Carrinho> lista = new ArrayList<>();
        String sql = "select car.ID_CLIENTE, caminho_max.ID_PRODUTO, car.STATUS, car.QUANTIDADE,SUM(car.VALOR *car.QUANTIDADE) AS VALOR, car.TOKEN_SESSION, caminho_max.CAMINHO  from (\n" +
                "SELECT ID_PRODUTO, min(b.CAMINHO) as CAMINHO\n" +
                "FROM CARRINHO a\n" +
                "INNER JOIN IMAGENS b ON a.ID_PRODUTO = b.ID_PRODUTO_FK\n" +
                "WHERE TOKEN_SESSION = ? \n" +
                "group by ID_PRODUTO\n" +
                ") as caminho_max\n" +
                "inner join CARRINHO car ON caminho_max.ID_PRODUTO = car.ID_PRODUTO\n" +
                "group by car.ID_CLIENTE, caminho_max.ID_PRODUTO, car.STATUS, car.QUANTIDADE,car.TOKEN_SESSION ,caminho_max.CAMINHO";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, session);
            Utils.printarMinhaConsulta(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("ID_CLIENTE");
                int idProduto = rs.getInt("ID_PRODUTO");
                String status = rs.getString("STATUS");
                int quantidade = rs.getInt("QUANTIDADE");
                double valor = rs.getDouble("VALOR");
                String sessionId = rs.getString("TOKEN_SESSION");
                String caminho = rs.getString("CAMINHO");
                lista.add(new Carrinho(idCliente, idProduto, status, quantidade, valor, sessionId,caminho));
            }
        } catch (Exception e) {
            Utils.printarErro("ERRO NA BUSCA" + e.getMessage());
            lista = null;
        }
        return lista;
    }

    public static boolean atualizar(Carrinho carrinho) throws IOException {
        String sql = gerarQueryAtt(carrinho);
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            if ((sql.contains("ID_CLIENTE"))) {
                ps.setInt(1, carrinho.get_idCliente());
            } else {
                ps.setString(1, carrinho.get_sessionId());
            }
            ps.setInt(2, carrinho.get_idProduto());
            Utils.printarMinhaConsulta(ps);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            Utils.printarErro(e.getMessage());
            return false;
        }
    }

    private static String gerarQueryAtt(Carrinho carrinho) {
        if (carrinho.get_idCliente() == 0) {
            return "UPDATE CARRINHO SET QUANTIDADE = QUANTIDADE + 1  WHERE TOKEN_SESSION = ? AND ID_PRODUTO = ?";
        } else {
            return "UPDATE CARRINHO SET QUANTIDADE = QUANTIDADE + 1 WHERE ID_CLIENTE = ? AND ID_PRODUTO = ?";
        }

    }

    private static String gerarQueryConsulta(Carrinho carrinho) {
        if (carrinho.get_idCliente() == 0) {
            return  "SELECT * FROM CARRINHO where ID_PRODUTO = ? AND TOKEN_SESSION = ?;";
        } else {
            return  "SELECT * FROM CARRINHO where ID_PRODUTO = ? AND ID_CLIENTE = ?;";
        }

    }

    public static boolean existeProduto(Carrinho carrinho) {
        String sql = gerarQueryConsulta(carrinho);
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, carrinho.get_idProduto());
            if(sql.contains(("ID_CLIENTE"))){
                ps.setInt(2,carrinho.get_idCliente());
            }else{
                ps.setString(2, carrinho.get_sessionId());
            }
            Utils.printarMinhaConsulta(ps);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException | IOException e) {
            Utils.printarErro(e.getMessage());
        }
        return false;
    }
}
