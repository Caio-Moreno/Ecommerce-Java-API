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
        List<ProdutoDto> listaProdutos = new ArrayList<>();


        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();

            gravaLog("Consulta produto" + ps, "ProdutoDAO", Level.INFO);

            while (rs.next()) {
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
                listaProdutos.add(new ProdutoDto(codProduto, nome, descricao, qualidade, categoria, statusProduto, qtdEstoque, preco, imagem1, plataforma));
                for (ProdutoDto prod : listaProdutos) {
                    gravaLog("Abrindo produto", "ProdutoDAO", Level.WARNING);
                    gravaLog("" + prod, "ProdutoDAO", Level.WARNING);
                }
            }

        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaProdutos;
    }

    public static List<Produto> consultaProdutoPorNome(String nomeProduto) throws IOException {
        List<Produto> listaProdutos = new ArrayList<>();


        gravaLog("Consulta produto por nome" + nomeProduto, "ProdutoDAO", Level.INFO);

        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,IMAGEM2,IMAGEM3,IMAGEM4,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK WHERE NOME = ?;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setString(1, nomeProduto);
            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            while (rs.next()) {
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
                listaProdutos.add(new Produto(codProduto, nome, descricao, qualidade, categoria, statusProduto, qtdEstoque, preco, new Imagem(imagem1, imagem2, imagem3, imagem4), plataforma));
                for (Produto prod : listaProdutos) {
                    gravaLog("Abrindo produto", "ProdutoDAO", Level.WARNING);
                    gravaLog("" + prod, "ProdutoDAO", Level.WARNING);
                }
            }

        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaProdutos;
    }

    public static boolean inserirProduto(Produto produto) throws SQLException, IOException {
        gravaLog("Inserir produto" + produto, "ProdutoDAO", Level.WARNING);
        Imagem imagem = produto.get_imagem();
        String sqlInsert = "INSERT INTO PRODUTO(ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA) VALUES(DEFAULT, ?, ?,?, ?, ?, ?,?);";
        String sqlInsertEstoque = "INSERT INTO ESTOQUE(ID, ID_PRODUTO_FK, QUANTIDADE) VALUES(DEFAULT, LAST_INSERT_ID(), ?);";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement insertProduto = con.prepareStatement(sqlInsert);
            PreparedStatement insertEstoque = con.prepareStatement(sqlInsertEstoque);
            insertProduto.setString(1, produto.get_nomeProduto());
            insertProduto.setString(2, produto.get_descricao());
            insertProduto.setDouble(3, produto.get_qualidadeProduto());
            insertProduto.setString(4, produto.get_categoria());
            insertProduto.setString(5, produto.get_statusProduto());
            insertProduto.setDouble(6, produto.get_preco());
            insertProduto.setString(7, produto.get_plataforma());

            gravaLog("Inserir produto" + insertProduto, "ProdutoDAO", Level.WARNING);
            /*        Insert do estoque */
            insertEstoque.setInt(1, produto.get_qtdEstoque());
            gravaLog("Inserir produto" + insertEstoque, "ProdutoDAO", Level.WARNING);

            insertProduto.execute();
            insertEstoque.execute();
            return true;
        } catch (SQLException e) {
            gravaLog(e.getMessage(), "ProdutoInsertDAO", Level.SEVERE);
            return false;
        }
    }
    public static  ProdutoDto retornarUltimoProduto() throws IOException {
        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK order by prod.ID desc limit 1;";
        ProdutoDto produto = null;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();
            gravaLog("Consulta produto" + ps, "ProdutoDAO", Level.INFO);
            while (rs.next()) {
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
                produto = new ProdutoDto(codProduto, nome, descricao, qualidade, categoria, statusProduto, qtdEstoque, preco, imagem1, plataforma);
                gravaLog("" + produto, "ProdutoDAO", Level.WARNING);
            }
        }catch (Exception e){
            gravaLog(e.getMessage(), "Busca ultimo", Level.SEVERE);
        }
        return produto;
    }
    public static boolean atualizarProduto(Produto produto, int id) throws IOException {
        Produto prod = null;
        String sqlUpdate = "UPDATE PRODUTO SET NOME = ?, DESCRICAO = ?, QUALIDADE = ?, CATEGORIA = ?, STATUS = ?, PRECO = ?, PLATAFORMA = ?, IMAGEM1 = ?, IMAGEM2 = ?, IMAGEM3 = ?, IMAGEM4 = ? WHERE ID = ?";
        boolean inseriu = true;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);
            ps.setString(1, produto.get_nomeProduto());
            ps.setString(2, produto.get_descricao());
            ps.setDouble(3, produto.get_qualidadeProduto());
            ps.setString(4, produto.get_categoria());
            ps.setString(5, produto.get_statusProduto());
            ps.setDouble(6, produto.get_preco());
            ps.setString(7, produto.get_plataforma());
            ps.setString(8,produto.get_imagem().getCaminhoImagem1());
            ps.setString(9,produto.get_imagem().getCaminhoImagem2());
            ps.setString(10,produto.get_imagem().getCaminhoImagem3());
            ps.setString(11,produto.get_imagem().getCaminhoImagem4());
            ps.setInt(12, id);
            ps.execute();
        }catch (SQLException | IOException e) {
            inseriu = false;
            gravaLog("Erro"+e.getMessage(), "ProdutoDAO", Level.SEVERE);
        }
        return inseriu;
    }
    public static Produto consultaProdutoPorId(int id) throws IOException {
        Produto produto = null;

        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,IMAGEM2,IMAGEM3,IMAGEM4,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK WHERE prod.ID = ?;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            System.out.println(ps);

            rs.next();

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

                produto = new Produto(codProduto, nome, descricao, qualidade, categoria, statusProduto, qtdEstoque, preco, new Imagem(imagem1, imagem2, imagem3, imagem4), plataforma);

            }catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return produto;
    }

    public static boolean deletarCliente(int id) throws IOException {
        gravaLog("DeletarCliente"+id,"ProdutoDAO", Level.SEVERE);
        String sqlDelete = "DELETE FROM PRODUTO WHERE ID = ?";
        boolean deletou = true;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete);
            ps.setInt(1,id);
            gravaLog("DeletarCliente"+ps,"ProdutoDAO", Level.SEVERE);
            ps.execute();

        }catch (Exception e){
            gravaLog("DeletarCliente erro"+e.getMessage(),"ProdutoDAO", Level.SEVERE);
            deletou = false;
        }
        return  deletou;
    }
}
