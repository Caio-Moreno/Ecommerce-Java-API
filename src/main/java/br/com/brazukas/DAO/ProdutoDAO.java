package br.com.brazukas.DAO;

import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.Models.Imagem;
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

    public static List<ProdutoDto> consultarProduto() throws SQLException, IOException {
        List <ProdutoDto> listaProdutos =  new ArrayList<>();


        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();

            gravaLog("Consulta produto" + ps, "ProdutoDAO",Level.INFO);

            while(rs.next()) {
                int codProduto = rs.getInt("ID");
                String nome = rs.getString("NOME");
                String descricao = rs.getString("DESCRICAO");
                double qualidade = rs.getDouble("QUALIDADE");
                String categoria = rs.getString("CATEGORIA");
                String statusProduto = rs.getString("STATUS");
                double preco = rs.getDouble("PRECO");
                String imagem1 = rs.getString("IMAGEM1");
                String plataforma = rs.getString("PLATAFORMA");
                int qtdEstoque = rs.getInt("QUANTIDADE");
                listaProdutos.add(new ProdutoDto(codProduto,nome,descricao, qualidade,categoria,statusProduto,qtdEstoque,preco,imagem1, plataforma));
                for (ProdutoDto prod : listaProdutos) {
                    gravaLog("Abrindo produto","ProdutoDAO", Level.WARNING);
                    gravaLog(""+prod,"ProdutoDAO", Level.WARNING);
                }
            }

        }catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->"+e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaProdutos;
    }

    public static List<Produto> consultaProdutoPorNome(String nomeProduto) throws IOException {
        List <Produto> listaProdutos =  new ArrayList<>();


        gravaLog("Consulta produto por nome"+nomeProduto, "ProdutoDAO",Level.INFO);

        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,IMAGEM2,IMAGEM3,IMAGEM4,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK WHERE NOME = ?;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setString(1,nomeProduto);
            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            while(rs.next()) {
                int codProduto = rs.getInt("ID");
                String nome = rs.getString("NOME");
                String descricao = rs.getString("DESCRICAO");
                double qualidade = rs.getDouble("QUALIDADE");
                String categoria = rs.getString("CATEGORIA");
                String statusProduto = rs.getString("STATUS");
                double preco = rs.getDouble("PRECO");
                String imagem1 = rs.getString("IMAGEM1");
                String imagem2 = rs.getString("IMAGEM2");
                String imagem3 = rs.getString("IMAGEM3");
                String imagem4 = rs.getString("IMAGEM4");
                String plataforma = rs.getString("PLATAFORMA");
                int qtdEstoque = rs.getInt("QUANTIDADE");
                listaProdutos.add(new Produto(codProduto,nome,descricao, qualidade,categoria,statusProduto,qtdEstoque,preco,new Imagem(imagem1,imagem2,imagem3,imagem4),plataforma));
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

    public static boolean inserirProduto(Produto produto) throws SQLException, IOException {
        gravaLog("Inserir produto", "ProdutoDAO", Level.WARNING);
        Imagem imagem = produto.get_imagem();
        String sqlInsert = "INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1, IMAGEM2, IMAGEM3, IMAGEM4) VALUES(DEFAULT, ?, ?,?, ?, ?, ?,?,?,?,?,?);";
        String sqlInsertEstoque = "INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, LAST_INSERT_ID(), ?);";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement insertProduto = con.prepareStatement(sqlInsert);
            PreparedStatement insertEstoque = con.prepareStatement(sqlInsertEstoque);
            insertProduto.setString(1, produto.get_nomeProduto());
            insertProduto.setString(2,produto.get_descricao());
            insertProduto.setDouble(3,produto.get_qualidadeProduto());
            insertProduto.setString(4,produto.get_categoria());
            insertProduto.setString(5,produto.get_statusProduto());
            insertProduto.setDouble(6,produto.get_preco());
            insertProduto.setString(7,produto.get_plataforma());
            insertProduto.setString(8, imagem.getCaminhoImagem1());
            insertProduto.setString(9,imagem.getCaminhoImagem2());
            insertProduto.setString(10,imagem.getCaminhoImagem3());
            insertProduto.setString(11,imagem.getCaminhoImagem4());

            gravaLog("Inserir produto"+insertProduto, "ProdutoDAO", Level.WARNING);
            /*        Insert do estoque */
            insertEstoque.setInt(1, produto.get_qtdEstoque());
            gravaLog("Inserir produto"+insertEstoque, "ProdutoDAO", Level.WARNING);

            insertProduto.execute();
            insertEstoque.execute();
            return true;
        }catch (SQLException e) {
            gravaLog(e.getMessage(), "ProdutoInsertDAO", Level.SEVERE);
            return false;
        }
    }
}
