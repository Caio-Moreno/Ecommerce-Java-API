package br.com.brazukas.DAO;

import br.com.brazukas.Models.Carrinho;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class CarrinhoDAO {

    public static boolean inserir(Carrinho carrinho) throws IOException {
        boolean inseriu = true;
        String sql = "INSERT INTO CARRINHO(ID_CLIENTE, ID_PRODUTO, STATUS, QUANTIDADE, VALOR) VALUES(?,?,?,?,?);";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, carrinho.get_idCliente());
            ps.setInt(2, carrinho.get_idProduto());
            ps.setString(3, carrinho.get_nomeProduto());
            ps.setInt(4,carrinho.get_quantidade());
            ps.setDouble(5,carrinho.get_valor());
            ps.executeUpdate();

        }catch (Exception e){
            gravaLog("EXCEPTION"+e.getMessage(), "CarrinhoDAO", Level.SEVERE);
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

            while (rs.next()){
                int idCliente = rs.getInt("ID_CLIENTE");
                int idProduto = rs.getInt("ID_PRODUTO");
                String status = rs.getString("STATUS");
                int quantidade = rs.getInt("QUANTIDADE");
                double valor = rs.getDouble("VALOR");
                lista.add(new Carrinho(idCliente,idProduto,status,quantidade,valor));
            }
        }catch (Exception e){
            gravaLog("ERRO NA BUSCA"+e.getMessage(), "CARRINHODAO", Level.SEVERE);
            lista = null;
        }
        return lista;
    }
}
