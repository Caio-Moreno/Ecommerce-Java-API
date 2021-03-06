package br.com.brazukas.DAO;

import br.com.brazukas.Models.Produto;
import br.com.brazukas.Models.Venda;
import br.com.brazukas.Models.VendaHasProduto;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class VendaDAO {

    public static List<Venda> consultarVenda() throws IOException {
        List<Venda> listaVenda = new ArrayList<>();
        String sqlConsulta = "SELECT a.ID, d.NOME, a.QUANTIDADE, a.VALOR_TOTAL, a.DATA_VENDA FROM VENDA a\n" +
                "INNER JOIN VENDA_HAS_PRODUTO b ON a.ID = b.ID_VENDA_FK\n" +
                "INNER JOIN PRODUTO c ON c.ID = b.ID_PRODUTO_FK\n" +
                "INNER JOIN CLIENTE d ON  d.ID = a.COD_CLIENTE\n" +
                "WHERE 1=1\n" +
                "GROUP BY a.ID\n" +
                "ORDER BY a.DATA_VENDA DESC; ";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id_venda = rs.getInt("ID");
                String nome = rs.getString("NOME");
                int quantidade = rs.getInt("QUANTIDADE");
                double valorTotal = rs.getDouble("VALOR_TOTAL");
                String dataVenda = rs.getString("DATA_VENDA");

                listaVenda.add(new Venda(id_venda,nome,quantidade,valorTotal,dataVenda));
            }
        }catch (Exception e){
            gravaLog("ERRO NA CONSULTA VENDA"+e.getMessage(), "VendaDAO", Level.SEVERE);
            listaVenda = null;
        }
        return  listaVenda;
    }

    public static Venda consultarVendaPorId(int id) {
        Venda venda = null;
        String sqlConsulta = "SELECT a.ID, d.NOME, a.QUANTIDADE, a.VALOR_TOTAL, a.DATA_VENDA FROM VENDA a\n" +
                "INNER JOIN VENDA_HAS_PRODUTO b ON a.ID = b.ID_VENDA_FK\n" +
                "INNER JOIN PRODUTO c ON c.ID = b.ID_PRODUTO_FK\n" +
                "INNER JOIN CLIENTE d ON  d.ID = a.COD_CLIENTE\n" +
                "WHERE 1=1\n" +
                "AND a.ID = ?\n"+
                "GROUP BY a.ID\n" +
                "ORDER BY a.DATA_VENDA DESC; ";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

            int id_venda = rs.getInt("ID");
            String nome = rs.getString("NOME");
            int quantidade = rs.getInt("QUANTIDADE");
            double valorTotal = rs.getDouble("VALOR_TOTAL");
            String dataVenda = rs.getString("DATA_VENDA");

            venda = new Venda(id_venda,nome,quantidade,valorTotal,dataVenda);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return venda;
    }

    public static boolean inserirVenda(VendaHasProduto venda) {
        boolean inseriu = true;
        String sqlInsert = "INSERT INTO(ID, DATA_VENDA, COD_CLIENTE, QUANTIDADE, DESCONTO,VALOR_TOTAL) VALUES(DEFAULT, ?,?,?,?,?);";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,venda.getVenda().get_dataVenda());
            ps.setInt(2,venda.getVenda().get_idCliente());
            ps.setInt(3,venda.getVenda().get_quantidade());
            ps.setDouble(4,venda.getVenda().get_desconto());
            ps.setDouble(5,venda.getVenda().get_valorTotal());
            int id = 0;
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            /*INSERI NA TABELA VENDA AGORA VOU INSERIR NA TABELA VENDA_HAS_PRODUTO*/
            String insertVendaHasProduto = "INSERT INTO VENDA_HAS_PRODUTO(ID_VENDA, ID_PRODUTO_FK, VALOR, QUANTIDADE) VALUES(?,?,?,?);";
            PreparedStatement ps2 = con.prepareStatement(insertVendaHasProduto);
            for(Produto prod : venda.getProdutos()) {
                ps.setInt(1,id);
                ps.setInt(2,prod.get_idProduto());
                ps.setDouble(3,prod.get_preco());
                ps.setInt(4,prod.get_qtdEstoque());
                ps.executeUpdate();
            }

        } catch (SQLException | IOException throwables) {
            inseriu = false;
        }
        return inseriu;
    }
}
