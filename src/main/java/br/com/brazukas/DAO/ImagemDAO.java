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

        String sqlUpdate = "INSERT IMAGENS(ID, CAMINHO, ID_PRODUTO_FK) VALUES(DEFAULT, ?, ?);";
        boolean inseriu = true;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);

            if(!(imagem.getCaminhoImagem1().isEmpty() || imagem.getCaminhoImagem1() == null)){
                ps.setString(1,imagem.getCaminhoImagem1());
                ps.setInt(2, id);
                ps.execute();
            }
            if(!(imagem.getCaminhoImagem2().isEmpty() || imagem.getCaminhoImagem2() == null)){
                ps.setString(1,imagem.getCaminhoImagem2());
                ps.setInt(2, id);
                ps.execute();
            }
            if(!(imagem.getCaminhoImagem3().isEmpty() || imagem.getCaminhoImagem3() == null)){
                ps.setString(1,imagem.getCaminhoImagem3());
                ps.setInt(2, id);
                ps.execute();
            }
            if(!(imagem.getCaminhoImagem4().isEmpty() || imagem.getCaminhoImagem4() == null)){
                ps.setString(1,imagem.getCaminhoImagem4());
                ps.setInt(2, id);
                ps.execute();
            }
        }catch (SQLException | IOException e) {
            inseriu = false;
            gravaLog("Erro"+e.getMessage(), "ProdutoDAO", Level.SEVERE);
        }
        return inseriu;
    }

}
