package br.com.brazukas.DAO;

import br.com.brazukas.Models.Imagem;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class ImagemDAO {

    public static boolean inserirImagens (Imagem imagem, int id) throws IOException {

        String sqlUpdate = "UPDATE PRODUTO SET IMAGEM1 = ?, IMAGEM2 = ?, IMAGEM3 = ?, IMAGEM4 = ? WHERE ID = ?";
        boolean inseriu = true;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);
            ps.setString(1,imagem.getCaminhoImagem1());
            ps.setString(2,imagem.getCaminhoImagem2());
            ps.setString(3,imagem.getCaminhoImagem3());
            ps.setString(4,imagem.getCaminhoImagem4());
            ps.setInt(5, id);
            ps.execute();
        }catch (SQLException | IOException e) {
            inseriu = false;
            gravaLog("Erro"+e.getMessage(), "ProdutoDAO", Level.SEVERE);
        }
        return inseriu;
    }

}
