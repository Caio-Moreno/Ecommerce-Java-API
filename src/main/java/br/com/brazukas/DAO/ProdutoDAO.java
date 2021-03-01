package br.com.brazukas.DAO;

import br.com.brazukas.Models.Produto;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class ProdutoDAO {

    public static List<Produto> consultarProduto() throws SQLException, IOException {
        List <Produto> listaProdutos =  new ArrayList<>();

        gravaLog("Consulta produto", "ProdutoDAO",Level.INFO);

        String sqlConsulta = "SELECT * FROM produto;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            while(rs.next()) {
                int codProduto = rs.getInt("idProduto");
                String nome = rs.getString("nomeProduto");
                String tipo = rs.getString("nomeExtenso");
                double qualidade = rs.getDouble("qualidadeProduto");
                String categoria = rs.getString("categoria");
                String statusProduto = rs.getString("statusProduto");
                int qtdEstoque = rs.getInt("qtdEstoque");
                double preco = rs.getDouble("preco");
                listaProdutos.add(new Produto(codProduto,nome,tipo, qualidade,categoria,statusProduto,qtdEstoque,preco));
                for (Produto prod : listaProdutos) {
                    gravaLog("Abrindo produto","ProdutoDAO", Level.WARNING);
                    gravaLog(""+prod,"ProdutoDAO", Level.WARNING);
                }
            }

        }catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->"+e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaProdutos;
    }
}
