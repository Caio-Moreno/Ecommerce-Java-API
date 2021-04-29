package br.com.brazukas.DAO;

import br.com.brazukas.Models.Endereco;
import br.com.brazukas.Models.EnderecoAlterar;
import br.com.brazukas.Util.ConexaoDb;
import com.amazonaws.services.kms.model.NotFoundException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class EnderecoDAO {

    public static boolean inserirEndereco(Endereco endereco, int idCliente) throws IOException {

        String sqlInserir = "INSERT INTO CLIENTE_ENDERECO (ID,CEP, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO,   CIDADE, ESTADO, TIPO, ID_CLIENTE_FK )" +
                "  VALUES (DEFAULT ,?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInserir);


            ps.setString(1, endereco.get_cep());
            ps.setString(2, endereco.get_logradouro());
            ps.setInt(3, endereco.get_numero());
            ps.setString(4, endereco.get_complemento());
            ps.setString(5, endereco.get_bairro());
            ps.setString(6, endereco.get_cidade());
            ps.setString(7, endereco.get_estado());
            ps.setString(8, endereco.get_tipo());
            ps.setInt(9,idCliente);

            System.out.println(ps);
            ps.execute();

            return true;

        } catch (SQLException e) {
            gravaLog(e.getMessage(), "EnderecoDAO", Level.SEVERE);
            return false;
        }
    }

    public static boolean atualizaStatus(int id, String status ) throws IOException {
        String sqlFaturamento = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ? AND TIPO = 'F';";// SE VIR 'F' RODA ESSE
        String sqlFaturamento2 = " UPDATE cliente_endereco SET TIPO = 'E' WHERE ID_CLIENTE_FK = ? AND TIPO = '*';";// SE VIR 'F' RODA ESSE
        String sqlEntrega = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ? AND TIPO = 'E';";// SE VIR 'E' RODA ESSE
        String sqlEntrega2 = " UPDATE cliente_endereco SET TIPO = 'F' WHERE ID_CLIENTE_FK = ? AND TIPO = '*';";// SE VIR 'E' RODA ESSE
        String sql = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ?;";// SE VIR * RODA ESSE
        String sqlBusca = "SELECT  1 as existe FROM cliente_endereco WHERE ID_CLIENTE_FK = ?;";
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
                return false;
            }
            if (status.equals("*")) {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
                return true;
            }
            if (status.equals("F")) {
                PreparedStatement ps = con.prepareStatement(sqlFaturamento);
                ps.setInt(1, id);
                ps.executeUpdate();
                PreparedStatement psF = con.prepareStatement(sqlFaturamento2);
                psF.setInt(1, id);
                psF.executeUpdate();
                return true;
            }
            if (status.equals("E")) {
                PreparedStatement ps = con.prepareStatement(sqlEntrega);
                ps.setInt(1, id);
                ps.executeUpdate();
                PreparedStatement psF = con.prepareStatement(sqlEntrega2);
                psF.setInt(1, id);
                psF.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Erro"+e.getMessage());
            gravaLog("ERRO PARA ATUALIZAR STATUS" + e.getMessage(), "USUARIODAO", Level.SEVERE);
        }
        return false;
    }

    public static boolean alterarEndereco(EnderecoAlterar endereco, int fk) throws IOException {
        String sqlFaturamento = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ? AND TIPO = 'F';";// SE VIR 'F' RODA ESSE
        String sqlFaturamento2 = " UPDATE cliente_endereco SET TIPO = 'E' WHERE ID_CLIENTE_FK = ? AND TIPO = '*';";// SE VIR 'F' RODA ESSE
        String sqlEntrega = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ? AND TIPO = 'E';";// SE VIR 'E' RODA ESSE
        String sqlEntrega2 = " UPDATE cliente_endereco SET TIPO = 'F' WHERE ID_CLIENTE_FK = ? AND TIPO = '*';";// SE VIR 'E' RODA ESSE
        String sql = " UPDATE cliente_endereco SET TIPO = 'I' WHERE ID_CLIENTE_FK = ?;";// SE VIR * RODA ESSE
        String sqlAltera = "  UPDATE cliente_endereco " +
                "SET TIPO = ?, CEP = ?, LOGRADOURO = ?, BAIRRO = ?, NUMERO = ?, COMPLEMENTO = ?, CIDADE = ?, ESTADO = ?" +
                "  WHERE ID = ?;";
        String sqlBusca  = "SELECT  1 as existe FROM cliente_endereco WHERE ID_CLIENTE_FK = ? AND ID = ?;";
        int temUser = 0;
        var tipoEndereco = endereco.get_tipo();
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps2 = con.prepareStatement(sqlBusca);
            ps2.setInt(1, fk);
            ps2.setInt(2, endereco.get_id());
            var rs = ps2.executeQuery();
            if (rs != null && rs.next()) {
                temUser = rs.getInt("existe");
            }

            if (temUser != 1) {
                throw new NotFoundException("Não existe Usuario com este ID.");
            }
            if (tipoEndereco.equals("*")) {
                ps2(fk,sql,con);
                ModificaEndereco(endereco,fk, sqlAltera, tipoEndereco, con);
                return true;
            }
            if (tipoEndereco.equals("F")) {
                ps(fk, sqlFaturamento, sqlFaturamento2, con);
                ModificaEndereco(endereco, fk ,sqlAltera, tipoEndereco, con);
                return true;
            }
            if (tipoEndereco.equals("E")) {
                ps(fk, sqlEntrega, sqlEntrega2, con);
                ModificaEndereco(endereco,fk ,sqlAltera, tipoEndereco, con);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Erro"+e.getMessage());
            gravaLog("ERRO PARA ATUALIZAR STATUS" + e.getMessage(), "USUARIODAO", Level.SEVERE);
        }
        return false;
    }

    private static void ModificaEndereco(EnderecoAlterar endereco,int fk, String sqlAltera, String tipoEndereco, Connection con) throws SQLException {
        try{
        PreparedStatement ps = con.prepareStatement(sqlAltera);
        ps.setString(1, tipoEndereco);
        ps.setString(2, endereco.get_cep());
        ps.setString(3, endereco.get_logradouro());
        ps.setString(4, endereco.get_bairro());
        ps.setInt(5, endereco.get_numero());
        ps.setString(6, endereco.get_complemento());
        ps.setString(7, endereco.get_cidade());
        ps.setString(8, endereco.get_estado());
        ps.setInt(9, endereco.get_id());
        ps.executeUpdate();
        }catch (SQLException e){
            throw new SQLException(e.getCause().getMessage());
        }

    }

    private static void ps(int id, String sql1, String sql2, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql1);
        ps.setInt(1, id);
        ps.executeUpdate();
        PreparedStatement psF = con.prepareStatement(sql2);
        psF.setInt(1, id);
        psF.executeUpdate();
    }

    private static void ps2(int id, String sql, Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public static boolean inativarEndereco(int id , String tipoEndereco) throws IOException {
        String sql = " UPDATE cliente_endereco SET TIPO = ? WHERE ID = ?;";
        String sqlBusca = "SELECT  1 as existe,TIPO FROM cliente_endereco WHERE ID =  ?;";
        int temUser = 0;
        String tipo = null;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps2 = con.prepareStatement(sqlBusca);
            ps2.setInt(1, id);
            var rs = ps2.executeQuery();
            if (rs != null && rs.next()) {
                temUser = rs.getInt("existe");
                tipo = rs.getString("TIPO");
            }

            if (temUser != 1 || tipo.equals("I")) {
                throw new NotFoundException("Não existe Endereço com este ID ou endereço já está inativo.");
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipoEndereco);
            ps.setInt(2, id);
            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Erro"+e.getMessage());
            gravaLog("ERRO PARA ATUALIZAR STATUS" + e.getMessage(), "USUARIODAO", Level.SEVERE);
        }
        return false;
    }

    public static List<EnderecoAlterar> listarEnderecosPorId (int id) throws  IOException{
        List<EnderecoAlterar> listaClientes = new ArrayList<>();
        gravaLog("Consulta cliente","ClienteDAO", Level.INFO);

        String sqlConsulta = "SELECT * FROM CLIENTE_ENDERECO WHERE ID_CLIENTE_FK = ? ;";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            while(rs.next()) {
                var endereco = EnderecoAlterar.builder()
                        ._cep(rs.getString("CEP"))
                        ._logradouro( rs.getString("LOGRADOURO"))
                        ._bairro( rs.getString("BAIRRO"))
                        ._numero( rs.getInt("NUMERO"))
                        ._complemento( rs.getString("COMPLEMENTO"))
                        ._cidade( rs.getString("CIDADE"))
                        ._estado( rs.getString("ESTADO"))
                        ._tipo( rs.getString("TIPO"))
                        ._id( rs.getInt("ID"))
                        .build();

                listaClientes.add(endereco);

            }

        } catch (SQLException | IOException e) {

            gravaLog("Erro de SQL Exception-->"+e.getMessage(), "ClienteDAO", Level.WARNING);
        }
        return listaClientes;
    }

}
