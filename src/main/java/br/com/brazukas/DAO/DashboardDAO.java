package br.com.brazukas.DAO;

import br.com.brazukas.Models.*;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.Utils;
import jdk.jshell.execution.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class DashboardDAO {
    public static ProdutoStatus consultaStatus() throws IOException {
        String sql = "select STATUS, count(*) as TOTAL from PRODUTO\n" +
                "GROUP by STATUS\n" +
                "ORDER BY STATUS ASC";
        ProdutoStatus produtoStatus = null;
        int ativo = 0;
        int inativo = 0;

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            for(int i = 0; i < 2; i++){
                rs.next();
                if(i == 0){
                    ativo = rs.getInt("TOTAL");
                }else {
                    inativo = rs.getInt("TOTAL");
                }
            }



        }catch (Exception e ){
            gravaLog("erro"+e.getMessage(), "PRODUTODAO", Level.SEVERE);
        }
        return produtoStatus = new ProdutoStatus(ativo,inativo);
    }

    public static ProdutoPlataforma consultaPlataforma() throws IOException {
        String sql = "SELECT PLATAFORMA, COUNT(*) TOTAL FROM PRODUTO\n" +
                "GROUP BY PLATAFORMA\n" +
                "ORDER BY PLATAFORMA ASC";
        ProdutoPlataforma produto = null;
        int ps4 = 0;
        int pc = 0;
        int ps5 = 0;
        int outros = 0;
        int xbox = 0;

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getString("PLATAFORMA").toLowerCase().trim().equals("ps4")) {
                    ps4 = rs.getInt("TOTAL");
                } else if (rs.getString("PLATAFORMA").toLowerCase().trim().equals("ps5")) {
                    ps5 = rs.getInt("TOTAL");
                } else if (rs.getString("PLATAFORMA").toLowerCase().trim().equals("pc")) {
                    pc = rs.getInt("TOTAL");
                } else if (rs.getString("PLATAFORMA").toLowerCase().trim().equals("xbox")) {
                    xbox = rs.getInt("TOTAL");
                } else {
                    outros = rs.getInt("TOTAL");
                }
            }
        }catch (Exception e ){
            gravaLog("erro"+e.getMessage(), "PRODUTODAO", Level.SEVERE);
        }
        return produto = new ProdutoPlataforma(ps4, pc, xbox, ps5, outros);
    }

    public static UsuarioStatus consultaStatusUsuario(){
        int cont = 0;
        int ativo = 0;
        int inativo = 0;
        String sql = "select STATUS, count(*) as TOTAL from USUARIO\n" +
                "                WHERE PERMISSAO = 'ADMIN' OR PERMISSAO = 'ESTOQUISTA' \n" +
                "                GROUP by STATUS\n" +
                "                ORDER BY STATUS ASC";

        UsuarioStatus status = new UsuarioStatus();

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            Utils.printarMinhaConsulta(ps);
            ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    if(cont == 0) {
                        ativo = rs.getInt("TOTAL");
                        status.set_ativo(ativo);
                        cont++;
                    }else{
                        inativo = rs.getInt("TOTAL");
                        status.set_inativo(inativo);
                    }
                }

        }catch (Exception e ){
            status = null;
            System.out.println("Erro"+e);
        }
        return status;
    }

    public static UsuarioCargo  consultaPorCargo(){
        String sql = "select PERMISSAO, count(*) as TOTAL from USUARIO\n" +
                "                WHERE PERMISSAO = 'ADMIN' OR PERMISSAO = 'ESTOQUISTA'\n" +
                "                GROUP by PERMISSAO\n" +
                "                ORDER BY PERMISSAO ASC";
        UsuarioCargo cargo = new UsuarioCargo();

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            rs.next();
            cargo.set_admin(rs.getInt("TOTAL"));

            rs.next();
            cargo.set_estoquista(rs.getInt("TOTAL"));

        }catch (Exception e){
            cargo = null;
            System.out.println("Erro"+e);
        }
        return cargo;
    }

    public static List<Ganhos> ganhos(String dataIni, String dataFim){
        String sql = "select DATA_VENDA, SUM(VALOR_TOTAL) as VALOR from VENDA\n" +
                "WHERE 1=1\n" +
                "AND DATA_VENDA BETWEEN ? AND ?\n" +
                "GROUP BY DATA_VENDA\n" +
                "ORDER BY DATA_VENDA ASC";
        List<Ganhos> listaGanhos = new ArrayList<>();

        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,dataIni);
            ps.setString(2,dataFim);
            Utils.printarMinhaConsulta(ps);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                listaGanhos.add(new Ganhos(rs.getString(1), rs.getDouble(2)));
            }


        }catch (Exception e){
            Utils.printarErro(e.getMessage() + " CLASSE DASHBOARD");
            listaGanhos = null;
        }

        return listaGanhos;
    }

    public static List<DashMaisVendidos> maisVendidos() {
        String sql = "SELECT NOME, SUM(QUANTIDADE) as total FROM VENDA_HAS_PRODUTO A\n" +
                "INNER JOIN PRODUTO B ON A.ID_PRODUTO_FK = B.ID\n" +
                "GROUP BY NOME\n" +
                "LIMIT 5";

        List<DashMaisVendidos> lista = new ArrayList<>();

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                lista.add(new DashMaisVendidos(rs.getString(1), rs.getInt(2)));
            }

        }catch (Exception e){
            Utils.printarErro(e.getMessage());
        }
        return  lista;
    }
}
