package br.com.brazukas.DAO;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kms.model.NotFoundException;

import br.com.brazukas.Models.Pedido;
import br.com.brazukas.Models.VendaHasProdutoJoin;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.Utils;
import br.com.brazukas.controller.Dto.PedidoDto;

public class PedidosDAO {

	
	public static boolean atualizaStatusPedido(int id, String status) throws IOException {
        String sql = "UPDATE VENDA SET STATUS = ? WHERE ID = ?";
        String sqlBusca = "SELECT  1 as existe FROM VENDA WHERE ID = ?;";
        int temUser = 0;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps2 = con.prepareStatement(sqlBusca);
            ps2.setInt(1, id);
            var rs = ps2.executeQuery();
            if (rs != null && rs.next()) {
                temUser = rs.getInt("existe");
            }

            if (temUser != 1) {
                throw new NotFoundException("Não existe Venda com este ID.");
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            System.out.println(ps);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro"+e.getMessage());
            gravaLog("ERRO PARA ATUALIZAR STATUS" + e.getMessage(), "PEDIDODAO", Level.SEVERE);
        }
        return false;
    }
	
	public static List<PedidoDto> consultarPedidoGeral() throws SQLException, IOException {
        List<PedidoDto> listaPedidos = new ArrayList<>();
        int i = 1;
        boolean jaExiste = false;
        String sqlConsulta = "SELECT ID, DATA_VENDA, VALOR_TOTAL, STATUS, NUM_PEDIDO FROM VENDA;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();

            gravaLog("Consulta Pedido" + ps, "PedidoDAO", Level.INFO);

            while (rs.next()) {
                int idVenda = rs.getInt("ID");
                String dataVenda = rs.getString("DATA_VENDA");
                double descricao = rs.getDouble("VALOR_TOTAL");
                String qualidade = rs.getString("STATUS");
                String categoria = rs.getString("NUM_PEDIDO");
             
                System.out.println("CODIGO RETORNADO DA PASSAGEM --->"+i+"----"+idVenda);


                if(i == 1) {
                    System.out.println("PASSEI PRIMEIRA VEZ"+idVenda);
                    listaPedidos.add(new PedidoDto(idVenda,dataVenda,descricao,qualidade,categoria));
                }else {
                    for (PedidoDto pedido : listaPedidos) {
                        if ((pedido.get_idVenda() == idVenda)) {
                            System.out.println("Pedido já existe"+pedido.get_idVenda());
                            jaExiste = true;
                            break;
                        }else{
                            jaExiste = false;
                        }
                    }
                    if(!jaExiste){
                        listaPedidos.add(new PedidoDto(idVenda,dataVenda,descricao,qualidade,categoria));
                    }
                }

                i++;
            }

        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "PedidoDAO", Level.WARNING);
        }
        return listaPedidos;
    }
	
    public static List<Pedido> getPedidos(int idCliente) {

        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT a.NUM_PEDIDO, a.ID, DATA_VENDA, VALOR_TOTAL, STATUS, c.TIPO  FROM BRAZUKAS.VENDA a\n" +
                "                INNER JOIN VENDA_HAS_PRODUTO b on a.ID = b.ID_VENDA_FK\n" +
                "                INNER JOIN CLIENTE_PAGAMENTO c on a.ID = c.ID_VENDA_FK\n" +
                "                WHERE a.COD_CLIENTE = ?\n" +
                "                GROUP BY a.ID,c.TIPO;";

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idCliente);

            Utils.printarMinhaConsulta(ps);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int i = 1;

                lista.add(new Pedido(rs.getString(i++),rs.getInt(i++), rs.getString(i++), rs.getDouble(i++), rs.getString(i++), rs.getString(i++)));
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
