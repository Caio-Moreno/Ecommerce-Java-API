package br.com.brazukas.DAO;

import br.com.brazukas.Models.ProdutoPlataforma;
import br.com.brazukas.Models.ProdutoStatus;
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


        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK WHERE STATUS = 'A';";

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
        String sqlUpdate = "UPDATE PRODUTO AS PRO INNER JOIN ESTOQUE AS EST ON PRO.ID = EST.ID_PRODUTO_FK SET PRO.NOME = ?, PRO.DESCRICAO = ?, PRO.QUALIDADE = ?, PRO.CATEGORIA = ?, PRO.STATUS = ?, PRO.PRECO = ?, PRO.PLATAFORMA = ?, EST.QUANTIDADE = ? WHERE PRO.ID = ?;";
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
            ps.setInt(8, produto.get_qtdEstoque());
            ps.setInt(9, id);
            ps.execute();
        }catch (SQLException | IOException e) {
            e.printStackTrace();
            inseriu = false;
//            gravaLog("Erro"+e.getMessage(), "ProdutoDAO", Level.SEVERE);
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
                Imagem imagem = Imagem.builder()
                        .caminhoImagem1(imagem1)
                        .caminhoImagem2(imagem2)
                        .caminhoImagem3(imagem3)
                        .caminhoImagem4(imagem4)
                        .build();

                produto = new Produto(codProduto, nome, descricao, qualidade, categoria, statusProduto, qtdEstoque, preco, imagem, plataforma);

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

    public static boolean existeProduto(int id) throws IOException {
        String sqlConsulta = "SELECT * FROM PRODUTO WHERE ID = ?";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println(ps);
            return rs.next();
        }catch (SQLException | IOException e) {

            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
            return  false;
        }
    }

    public static boolean atualizaStatus(int id, String status) throws IOException {
        String sql = "UPDATE PRODUTO SET STATUS = ? WHERE ID = ?";
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,status);
            ps.setInt(2,id);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            gravaLog("ERRO PARA ATUALIZAR STATUS"+e.getMessage(), "PRODUTODAO", Level.SEVERE);
        }
        return  false;
    }

    public static List<ProdutoDto> consultarProdutoGeral() throws SQLException, IOException {
        List<ProdutoDto> listaProdutos = new ArrayList<>();
        String sqlConsulta = "SELECT prod.ID as ID, NOME, DESCRICAO, QUALIDADE, CATEGORIA, STATUS, PRECO, PLATAFORMA, IMAGEM1,QUANTIDADE FROM PRODUTO prod INNER JOIN ESTOQUE est ON prod.ID = est.ID_PRODUTO_FK";

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
}
