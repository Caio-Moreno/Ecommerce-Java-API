package br.com.brazukas.DAO;

import br.com.brazukas.Models.Carrinho;
import br.com.brazukas.Util.ConexaoDb;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CarrinhoDAO {

    public static boolean inserir(Carrinho carrinho){
        boolean inseriu = true;
        String sql = "INSERT INTO(ID_PRODUTO, NOME, QUANTIDADE, VALOR) VALUES(?,?,?,?);";
        try{
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

        }catch (Exception e){
            inseriu = false;
        }
        return inseriu;
    }
}
