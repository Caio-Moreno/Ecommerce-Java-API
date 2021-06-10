package br.com.brazukas.DAO;

import br.com.brazukas.Models.Cliente;
import br.com.brazukas.Models.Produto;
import br.com.brazukas.Models.UserAlterar;
import br.com.brazukas.Models.UsuarioInterno;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.controller.Dto.ProdutoDto;
import br.com.brazukas.controller.Dto.UsuarioInternoDto;
import br.com.brazukas.enums.StatusEnum;
import br.com.brazukas.enums.TipoUsuarioInternoEnum;
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

public class UsuarioInternoDAO {


    public static List<UsuarioInternoDto> listarUsuariosInternosAtivos() throws SQLException, IOException {
        int i = 1;
        boolean jaExiste = false;
        List<UsuarioInternoDto> listaUsuariosInterno = new ArrayList<>();


        String sqlConsulta = "SELECT ID,NOME,PERMISSAO,STATUS FROM USUARIO WHERE STATUS = 'A' and PERMISSAO = 'ADMIN' OR PERMISSAO = 'ESTOQUISTA';";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            gravaLog("Listar Usúarios Interno" + ps, "UsuariosInternoDAO", Level.INFO);

            while (rs.next()) {
                UsuarioInternoDto usuarioInternoDto = UsuarioInternoDto.builder()
                        ._id(rs.getInt("ID"))
                        ._nomeUsuarioInterno(rs.getString("NOME"))
                        ._tipoUsuarioInternoEnum(TipoUsuarioInternoEnum.valueOf(rs.getString("PERMISSAO")))
                        ._statusEnum(StatusEnum.valueOf(rs.getString("STATUS")))
                        .build();

                System.out.println("CODIGO RETORNADO DA PASSAGEM --->" + i + "----" + usuarioInternoDto.get_nomeUsuarioInterno());


                if (i == 1) {
                    System.out.println("PASSEI PRIMEIRA VEZ" + usuarioInternoDto.get_nomeUsuarioInterno());
                    listaUsuariosInterno.add(usuarioInternoDto);
                } else {
                    for (UsuarioInternoDto prod : listaUsuariosInterno) {
                        if ((prod.get_nomeUsuarioInterno() == usuarioInternoDto.get_nomeUsuarioInterno())) {
                            System.out.println("Produto já existe" + prod.get_nomeUsuarioInterno());
                            jaExiste = true;
                            break;
                        } else {
                            jaExiste = false;
                        }
                    }
                    System.out.println("Valor do jaExiste" + jaExiste);
                    if (!jaExiste) {
                        System.out.println("Vou adicionar pois o valor do ja existe" + jaExiste);
                        listaUsuariosInterno.add(usuarioInternoDto);
                    }
                }

                i++;
            }

        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaUsuariosInterno;
    }


    public static List<UsuarioInternoDto> listarUsuariosInterno() throws SQLException, IOException {
        int i = 1;
        boolean jaExiste = false;
        List<UsuarioInternoDto> listaUsuariosInterno = new ArrayList<>();


        String sqlConsulta = "SELECT ID,NOME,PERMISSAO,STATUS FROM USUARIO WHERE  PERMISSAO = 'ADMIN' OR PERMISSAO = 'ESTOQUISTA' ORDER BY ID ASC;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            gravaLog("Listar Usúarios Interno" + ps, "UsuariosInternoDAO", Level.INFO);

            while (rs.next()) {
                UsuarioInternoDto usuarioInternoDto = UsuarioInternoDto.builder()
                        ._id(rs.getInt("ID"))
                        ._nomeUsuarioInterno(rs.getString("NOME"))
                        ._tipoUsuarioInternoEnum(TipoUsuarioInternoEnum.valueOf(rs.getString("PERMISSAO")))
                        ._statusEnum(StatusEnum.valueOf(rs.getString("STATUS")))
                        .build();

                System.out.println("CODIGO RETORNADO DA PASSAGEM --->" + i + "----" + usuarioInternoDto.get_nomeUsuarioInterno());


                if (i == 1) {
                    System.out.println("PASSEI PRIMEIRA VEZ" + usuarioInternoDto.get_nomeUsuarioInterno());
                    listaUsuariosInterno.add(usuarioInternoDto);
                } else {
                    for (UsuarioInternoDto prod : listaUsuariosInterno) {
                        if ((prod.get_nomeUsuarioInterno() == usuarioInternoDto.get_nomeUsuarioInterno())) {
                            System.out.println("Produto já existe" + prod.get_nomeUsuarioInterno());
                            jaExiste = true;
                            break;
                        } else {
                            jaExiste = false;
                        }
                    }
                    System.out.println("Valor do jaExiste" + jaExiste);
                    if (!jaExiste) {
                        System.out.println("Vou adicionar pois o valor do ja existe" + jaExiste);
                        listaUsuariosInterno.add(usuarioInternoDto);
                    }
                }

                i++;
            }

        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ProdutoDAO", Level.WARNING);
        }
        return listaUsuariosInterno;
    }

    public static boolean atualizaStatus(int id, String status) throws IOException {
        String sql = "UPDATE USUARIO SET STATUS = ? WHERE ID = ?";
        String sqlBusca = "SELECT  1 as existe FROM USUARIO WHERE id = ?;";
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
                throw new NotFoundException("Não existe Usuario com este ID.");
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            System.out.println(ps);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro"+e.getMessage());
            gravaLog("ERRO PARA ATUALIZAR STATUS" + e.getMessage(), "USUARIODAO", Level.SEVERE);
        }
        return false;
    }

    public static boolean atualizarUsuarioInterno(UserAlterar userAlterar, int id) throws IOException {
        String sqlUpdate = "";
        boolean inseriu = true;

        if (userAlterar.get_password().equals("") || userAlterar.get_password() == null) {
            sqlUpdate = "UPDATE USUARIO SET NOME = ?, PERMISSAO = ?  WHERE ID = ?";
        }else{
            sqlUpdate = "UPDATE USUARIO SET NOME = ?,PASSWORD = ?, PERMISSAO = ?  WHERE ID = ?";
        }

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate);

            if(sqlUpdate.contains("PASSWORD")) {
                ps.setString(1, userAlterar.get_nome());
                ps.setString(2, userAlterar.get_password());
                ps.setString(3, userAlterar.get_tipoUser().getTipo());
                ps.setInt(4, id);
            }else {
                ps.setString(1, userAlterar.get_nome());
                ps.setString(2, userAlterar.get_tipoUser().getTipo());
                ps.setInt(3, id);
            }
            System.out.println("Minha query"+ps);
            ps.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            inseriu = false;
        }
        return inseriu;
    }

    public static UserAlterar consultaUsuarioPorId(int id) throws IOException {
        UserAlterar userAlterar = null;
        boolean existeImagem = false;
        int i = 1;

        String sqlConsulta = "SELECT NOME,CPF,PERMISSAO,SEXO,DATANASCIMENTO,EMAIL FROM USUARIO WHERE ID = ?;";

        try {

            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userAlterar = UserAlterar.builder()
                        ._nome(rs.getString("NOME"))
                        ._cpf(rs.getString("CPF"))
                        ._tipoUser(TipoUsuarioInternoEnum.valueOf(rs.getString("PERMISSAO")))
                        ._sexo(rs.getString("SEXO"))
                        ._dataNascimento(rs.getString("DATANASCIMENTO"))
                        ._email(rs.getString("EMAIL"))
                        ._password("")
                        .build();

            }
        } catch (SQLException | IOException e) {
            gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
        }
        return userAlterar;

    }

    public static boolean inserirUsuarioInterno(UsuarioInterno usuarioInterno) throws IOException, SQLException {

        String sqlInserir = "INSERT INTO USUARIO (`ID`,`NOME`, `CPF`, `SEXO`, `DATANASCIMENTO`, `EMAIL`, `PASSWORD`, `PERMISSAO`,  `STATUS`)" +
                " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?,  'A');";

        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInserir);


            ps.setString(1, usuarioInterno.get_nome());
            ps.setString(2, usuarioInterno.get_cpf());
            ps.setString(3, usuarioInterno.get_sexo());
            ps.setString(4, usuarioInterno.get_dataNascimento());
            ps.setString(5, usuarioInterno.get_email());
            ps.setString(6, usuarioInterno.get_password());
            ps.setString(7, usuarioInterno.get_tipoUser().getTipo());
            System.out.println(ps);
            ps.execute();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro"+e.getMessage());
            return false;
        }
    }
    public static UsuarioInterno retornarUltimoUsuarioInterno() throws IOException {
        String sqlConsulta = "SELECT  ID, NOME, CPF, SEXO, DATANASCIMENTO, EMAIL, PASSWORD, PERMISSAO, STATUS FROM USUARIO  order by ID desc limit 1;";
        UsuarioInterno usuarioInterno = null;
        try {
            Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();
            gravaLog("Consulta usúario Interno" + ps, "UsuarioInternoDAO", Level.INFO);
            while (rs.next()) {
                usuarioInterno = UsuarioInterno.builder()
                        ._id(rs.getInt("ID"))
                        ._nome(rs.getString("NOME"))
                        ._cpf(rs.getString("CPF"))
                        ._sexo(rs.getString("SEXO"))
                        ._dataNascimento(rs.getString("DATANASCIMENTO"))
                        ._email(rs.getString("EMAIL"))
                        ._password(rs.getString("PASSWORD"))
                        ._tipoUser(TipoUsuarioInternoEnum.valueOf(rs.getString("PERMISSAO")))
                        ._status(StatusEnum.valueOf(rs.getString("STATUS")))
                        .build();
                gravaLog("" + usuarioInterno, "UsuarioInternoDAO", Level.WARNING);
            }
        }catch (Exception e){
            gravaLog(e.getMessage(), "Busca ultimo", Level.SEVERE);
        }
        return usuarioInterno;
    }
}
