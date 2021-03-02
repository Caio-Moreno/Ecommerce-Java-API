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
/*
    public static void inserirProduto(produto produto) throws SQLException, IOException {
        gravaLog("Inserir produto", "ProdutoDAO", Level.WARNING);
        String sqlInsert = "insert into PRODUTO(ID_PRODUTO,COD_FILIAL,NOME,TIPO,VALOR_COMPRA,VALOR_VENDA,PATH) values(DEFAULT,?,?,?,?,?,?)";
        String sqlInsertEstoque = "insert into ESTOQUE(ID_ESTOQUE,COD_PRODUTO_FK,QUANTIDADE) values(DEFAULT,LAST_INSERT_ID(),?)";

        try {
            Connection con = ConexaoDB.getConnection();
            PreparedStatement insertProduto = con.prepareStatement(sqlInsert);
            PreparedStatement insertEstoque = con.prepareStatement(sqlInsertEstoque);
            insertProduto.setInt(1, produto.get_codFilial());
            insertProduto.setString(2, produto.get_nome());
            insertProduto.setString(3, produto.get_tipo());
            insertProduto.setDouble(4, produto.get_valorCompra());
            insertProduto.setDouble(5, produto.get_valorVenda());
            insertProduto.setString(6, produto.get_path());
            System.out.println("INSERT DO PRODUTO---->"+insertProduto);
            /*        Insert do estoque
            insertEstoque.setInt(1, produto.get_quantidade());
            System.out.println("INSERT DO ESTOQUE---->"+insertEstoque);

            insertProduto.execute();
            insertEstoque.execute();

        }catch (SQLException e) {
            throw new ServletException(e.getMessage());
        }
        System.out.println("executei");
    }*/
}
