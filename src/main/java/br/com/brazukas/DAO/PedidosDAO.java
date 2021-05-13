package br.com.brazukas.DAO;

import br.com.brazukas.Models.Frete;
import br.com.brazukas.Models.Pedido;
import br.com.brazukas.Models.VendaHasProduto;
import br.com.brazukas.Models.VendaHasProdutoJoin;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAO {

    public static List<Pedido> getPedidos(int idCliente) {

        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT ID, DATA_VENDA, VALOR_TOTAL, STATUS  FROM BRAZUKAS.VENDA a\n" +
                "INNER JOIN VENDA_HAS_PRODUTO b on a.ID = b.ID_VENDA_FK\n" +
                "WHERE a.COD_CLIENTE = ?\n" +
                "GROUP BY a.ID;";

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idCliente);

            Utils.printarMinhaConsulta(ps);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int i = 1;

                lista.add(new Pedido(rs.getInt(i++), rs.getString(i++), rs.getDouble(i++), rs.getString(i++)));
            }

            return  lista;

        }catch (Exception e){
            Utils.printarErro("Erro para buscar pedidos"+e.getMessage());
        }
        return null;
    }

    public static List<VendaHasProdutoJoin> getPedidosProdutos(int idVenda) {

        List<VendaHasProdutoJoin> lista = new ArrayList<>();

        String sql = "SELECT ID_PRODUTO_FK, VALOR, QUANTIDADE, b.NOME FROM BRAZUKAS.VENDA_HAS_PRODUTO a \n" +
                "INNER JOIN PRODUTO b ON a.ID_PRODUTO_FK = b.ID\n" +
                "where ID_VENDA_FK = ?;";

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idVenda);

            Utils.printarMinhaConsulta(ps);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int i = 1;

                lista.add(new VendaHasProdutoJoin(rs.getInt(i++), rs.getDouble(i++), rs.getInt(i++), rs.getString(i++)));
            }

            return  lista;

        }catch (Exception e){
            Utils.printarErro("Erro para buscar pedidos"+e.getMessage());
        }
        return null;
    }


}
