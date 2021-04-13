package br.com.brazukas.DAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import br.com.brazukas.Models.Cliente;
import br.com.brazukas.Models.Endereco;
import br.com.brazukas.Util.ConexaoDb;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class ClienteDAO {
	
	public static boolean alterarCliente (Cliente cliente) throws IOException, SQLException{
		String sqlAlterar = "UPDATE CLIENTE SET NOME = ?, SEXO = ?, DATANASCIMENTO = ?, TELEFONE = ?, EMAIL = ?, CEP = ?, ENDERECO = ?, BAIRRO = ?, NUMERO = ?, COMPLEMENTO = ?, CIDADE = ?, ESTADO = ? "
				+ "WHERE CPF = ? ;";
		
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlAlterar);
			
			int i = 1;
			
			ps.setString(i++, cliente.get_nome());
			ps.setString(i++, cliente.get_sexo());
			ps.setString(i++, cliente.get_dataNascimento());
			ps.setString(i++, cliente.get_telefone() );
			ps.setString(i++, cliente.get_email());
			ps.setString(i++, cliente.get_cpf());
			
			ps.execute();
			
			return true;
			
		}catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
			return false;
		}
		
	}
	
	public static boolean excluirCliente (String cpf) throws IOException, SQLException{
		String sqlExcluir = "DELETE FROM CLIENTE WHERE CPF = ? ;";
				
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlExcluir);
			
			ps.setString(1, cpf);
			
			ps.execute();
			
			return true;
		}catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
			return false;
		}
		
	}
	
	
	public static boolean inserirCliente (Cliente cliente) throws IOException, SQLException{
		int idCliente = 0;
		String sqlInserirC = "INSERT INTO USUARIO (ID, NOME, CPF, SEXO, DATANASCIMENTO, EMAIL,PASSWORD,PERMISSAO,STATUS)\n" +
							"VALUES ( DEFAULT, ?, ?, ?, ?, ?,  ?, 'CLIENTE','A') ;";
		String sqlInserirE = "INSERT INTO CLIENTE_ENDERECO(ID_CLIENTE_FK, CEP, LOGRADOURO, BAIRRO, NUMERO, COMPLEMENTO, CIDADE,ESTADO,TIPO)\n" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		String sqlInserirT = "INSERT INTO CLIENTE_TELEFONE(ID_CLIENTE_FK, TELEFONE)\n" +
				"VALUES (?, ?);";

		
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement psC = con.prepareStatement(sqlInserirC, Statement.RETURN_GENERATED_KEYS);
			int i = 1;

			psC.setString(i++, cliente.get_nome());
			psC.setString(i++, cliente.get_cpf());
			psC.setString(i++, cliente.get_sexo() );
			psC.setString(i++, cliente.get_dataNascimento());
			psC.setString(i++, cliente.get_email());
			psC.setString(i++, cliente.get_senha());
			System.out.println("Insert na tabela cliente -->>"+psC);
			psC.execute();

			ResultSet rs = psC.getGeneratedKeys();
			if(rs.next()){
				idCliente = rs.getInt(1);
			}



			PreparedStatement psE = con.prepareStatement(sqlInserirE);
			for (Endereco end : cliente.get_endereco()) {
				i = 1;
				psE.setInt(i++, idCliente);
				psE.setString(i++, end.get_cep());
				psE.setString(i++, end.get_logradouro());
				psE.setString(i++, end.get_bairro());
				psE.setInt(i++, end.get_numero());
				psE.setString(i++, end.get_complemento());
				psE.setString(i++, end.get_cidade());
				psE.setString(i++, end.get_estado());
				psE.setString(i++, end.get_tipo());
				System.out.println("Insert na tabela endereco -->>"+psE);
				psE.execute();
			}

			PreparedStatement psT = con.prepareStatement(sqlInserirT);
			i = 1;
			psT.setInt(i++,idCliente);
			psT.setString(i++,cliente.get_telefone());
			System.out.println("Insert na tabela endereco -->>"+psE);
			psT.execute();

			return true;
		
		}catch (SQLException e) {
			System.out.println("Erro para inserir"+e.getMessage());
			return false;
		}
	}
	
	public static List<Cliente> consultarCliente () throws SQLException, IOException{
		List<Cliente> listaClientes = new ArrayList<>();
		gravaLog("Consulta cliente","ClienteDAO", Level.INFO);
		
		String sqlConsulta = "SELECT * FROM CLIENTE ;";
		
		try {
			Connection con = ConexaoDb.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlConsulta);
            ResultSet rs = ps.executeQuery();
            
            System.out.println(ps);
            
            while(rs.next()) {
            	int idCliente = rs.getInt("ID");
				String nome = rs.getString("NOME");
				String cpf  = rs.getString("CPF");
				String sexo = rs.getString("SEXO");
				String dataNascimento = rs.getString("DATANASCIMENTO");
				String telefone = rs.getString("TELEFONE");
				String email = rs.getString("EMAIL");
				String cep = rs.getString("CEP");
				String endereco = rs.getString("ENDERECO");
				String bairro = rs.getString("BAIRRO");
				int numero = rs.getInt("NUMERO");
				String complemento = rs.getString("COMPLEMENTO");
				String cidade = rs.getString("CIDADE");
				String estado = rs.getString("ESTADO");
				
				//listaClientes.add(new Cliente(idCliente, nome, cpf, sexo, dataNascimento, telefone, email, cep, endereco, bairro, numero, complemento, cidade, estado));
			           
            }
            
		} catch (SQLException | IOException e) {
			 
        	gravaLog("Erro de SQL Exception-->"+e.getMessage(), "ClienteDAO", Level.WARNING);
        }
		return listaClientes;
	}
	
	public static Cliente getCliente(String cpf) throws IOException {
		
		gravaLog("Consulta cliente por cpf" + cpf, "ClienteDAO", Level.INFO);

		Cliente cliente = null;
		String sqlConsulta = "SELECT * FROM CLIENTE WHERE CPF = ? ; ";
		
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);

			ps.setString(1, cpf);

			ResultSet rs = ps.executeQuery();

			System.out.println(ps);

			while(rs.next()) {
				int idCliente = rs.getInt("ID");
				String nome = rs.getString("NOME");
				String sexo = rs.getString("SEXO");
				String dataNascimento = rs.getString("DATANASCIMENTO");
				String telefone = rs.getString("TELEFONE");
				String email = rs.getString("EMAIL");
				String cep = rs.getString("CEP");
				String endereco = rs.getString("ENDERECO");
				String bairro = rs.getString("BAIRRO");
				int numero = rs.getInt("NUMERO");
				String complemento = rs.getString("COMPLEMENTO");
				String cidade = rs.getString("CIDADE");
				String estado = rs.getString("ESTADO");
			
				//cliente = new Cliente (idCliente, nome, cpf, sexo, dataNascimento, telefone, email, cep, endereco, bairro, numero, complemento, cidade, estado);
		
			}	

		
		} catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
		}

		return cliente;
	}

	public static boolean clienteExite(int id) throws IOException {

		gravaLog("Consulta cliente por cpf" + id, "ClienteDAO", Level.INFO);
		String sqlConsulta = "SELECT * FROM CLIENTE WHERE ID = ? ; ";

		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			return rs.next();
		} catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
			return false;
		}
	}

	public static boolean ExisteEmail(String email) {

		String sqlConsulta = 	"SELECT * FROM USUARIO\n" +
				"WHERE 1=1\n" +
				"AND EMAIL = ?;";

		try{
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);
			ps.setString(1,email);
			System.out.println("Minha consulta -->>"+ps);
			ResultSet rs = ps.executeQuery();

			return rs.next();

		}catch (Exception e){
			System.out.println("Erro"+e.getMessage());
		}

		return false;
	}

	public static boolean ExisteCpf(String cpf) {
		String sqlConsulta = 	"SELECT * FROM USUARIO\n" +
				"WHERE 1=1\n" +
				"AND CPF = ?;";

		try{
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);
			ps.setString(1,cpf);
			System.out.println("Minha consulta -->>"+ps);
			ResultSet rs = ps.executeQuery();

			return rs.next();

		}catch (Exception e){
			System.out.println("Erro"+e.getMessage());
		}

		return false;

	}
}
