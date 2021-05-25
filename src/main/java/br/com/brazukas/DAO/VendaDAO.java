package br.com.brazukas.DAO;

import br.com.brazukas.Models.Payment;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.Models.Venda;
import br.com.brazukas.Models.VendaHasProduto;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.Utils;
import jdk.jshell.execution.Util;

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

    public static int inserirVenda(VendaHasProduto venda,String numPedido) {


        String sqlInsert = "INSERT INTO VENDA(ID, DATA_VENDA, COD_CLIENTE, QUANTIDADE,VALOR_TOTAL, STATUS, NUM_PEDIDO,ID_ENTREGA) VALUES(DEFAULT, curdate(),?,?,?,?,?, ?);";
        int id = 0;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,venda.getVenda().get_idCliente());
            ps.setInt(2,venda.getVenda().get_quantidade());
            ps.setDouble(3,venda.getVenda().get_valorTotal());
            ps.setString(4,"PENDING PAYMENT");
            ps.setString(5,numPedido);
            ps.setInt(6, venda.getVenda().get_idEndereco());

            Utils.printarMinhaConsulta(ps);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            /*INSERI NA TABELA VENDA AGORA VOU INSERIR NA TABELA VENDA_HAS_PRODUTO*/
            String insertVendaHasProduto = "INSERT INTO VENDA_HAS_PRODUTO(ID_VENDA_FK, ID_PRODUTO_FK, VALOR, QUANTIDADE) VALUES(?,?,?,?);";
            PreparedStatement ps2 = con.prepareStatement(insertVendaHasProduto);
            for(Produto prod : venda.getProdutos()) {
                ps2.setInt(1,id);
                ps2.setInt(2,prod.get_idProduto());
                ps2.setDouble(3,prod.get_preco());
                ps2.setInt(4,prod.get_qtdEstoque());
                ps2.executeUpdate();
            }

            CarrinhoDAO.deletaCarrinho(venda.getVenda().get_idCliente());

        } catch (Exception e) {
            Utils.printarErro("erro"+e.getMessage());
            id = 0;
        }




        return id;
    }

    public static boolean insertPayment(Payment payment) {
        String query ="INSERT INTO CLIENTE_PAGAMENTO(ID,NAME_CART,NUMBER_CART,ID_VENDA_FK,VALOR,QTD_PARCELAS,PAYPAL_EMAIL,PAYPAL_CPF,NUM_BOLETO,MP_EMAIL,MP_CPF, TIPO) VALUES (DEFAULT, ?,?, ?,?, ?,?, ?,?, ?,?,?)";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            int i = 1;
            ps.setString(i++,payment.get_nameCart());
            ps.setString(i++,payment.get_numberCart());
            ps.setInt(i++,payment.get_idVendaFk());
            ps.setDouble(i++,payment.get_valor());
            ps.setInt(i++,payment.get_qtdParcelas());
            ps.setString(i++,payment.get_paypalEmail());
            ps.setString(i++,payment.get_paypalCpf());
            ps.setString(i++,payment.get_numBoleto());
            ps.setString(i++,payment.get_mpEmail());
            ps.setString(i++,payment.get_mpCpf());
            ps.setString(i++,payment.get_tipo());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            Utils.printarErro("Erro"+e.getMessage());
        }
        return false;
    }

    public static String numPedido(int idVenda){
        String sql = "select NUM_PEDIDO from VENDA where ID = ?";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idVenda);

            ResultSet rs = ps.executeQuery();

            rs.next();

            return  rs.getString("NUM_PEDIDO");


        }catch (Exception e){
            Utils.printarErro(e.getMessage());
        }

        return "";
    }

    public static Payment formaPagamento(int idVenda){
        Payment payment;
                String sql = "SELECT * FROM BRAZUKAS.CLIENTE_PAGAMENTO where ID_VENDA_FK = ?;";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,idVenda);

            ResultSet rs = ps.executeQuery();

            rs.next();
            int i = 1;


             payment = new Payment(
                    rs.getInt(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getInt(i++),
                    rs.getDouble(i++),
                    rs.getInt(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++),
                    rs.getString(i++)
            );
             Utils.printarNaTela("MEU PAGAMENTO -->>>");
            System.out.println(payment);

            return  payment;




        }catch (Exception e){
            Utils.printarErro(e.getMessage());
        }
        return null;
    }
}
