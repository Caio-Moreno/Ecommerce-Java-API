package br.com.brazukas.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import br.com.brazukas.Models.Cliente;
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
			ps.setString(i++, cliente.get_cep());
			ps.setString(i++, cliente.get_endereco());
			ps.setString(i++, cliente.get_bairro());
			ps.setInt(i++, cliente.get_numero());
			ps.setString(i++, cliente.get_complemento() );
			ps.setString(i++, cliente.get_cidade());
			ps.setString(i++, cliente.get_estado());
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
		String sqlInserir = "INSERT INTO CLIENTE (ID, NOME, CPF, SEXO, DATANASCIMENTO, TELEFONE, EMAIL, CEP, ENDERECO, BAIRRO, NUMERO, COMPLEMENTO, CIDADE, ESTADO) VALUES"
				+ "( DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ;";
		
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlInserir);
			
			int i = 1;
			
			ps.setString(i++, cliente.get_nome());
			ps.setString(i++, cliente.get_cpf());
			ps.setString(i++, cliente.get_sexo() );
			ps.setString(i++, cliente.get_dataNascimento() );
			ps.setString(i++, cliente.get_telefone() );
			ps.setString(i++, cliente.get_email());
			ps.setString(i++, cliente.get_cep());
			ps.setString(i++, cliente.get_endereco());
			ps.setString(i++, cliente.get_bairro());
			ps.setInt(i++, cliente.get_numero());
			ps.setString(i++, cliente.get_complemento() );
			ps.setString(i++, cliente.get_cidade());
			ps.setString(i++, cliente.get_estado());
						
			ps.execute();
			
			return true;
		
		}catch (SQLException e) {
			gravaLog(e.getMessage(), "ClienteInsertDAO", Level.SEVERE);
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
				
				listaClientes.add(new Cliente(idCliente, nome, cpf, sexo, dataNascimento, telefone, email, cep, endereco, bairro, numero, complemento, cidade, estado));
			           
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
			
				cliente = new Cliente (idCliente, nome, cpf, sexo, dataNascimento, telefone, email, cep, endereco, bairro, numero, complemento, cidade, estado);
		
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

}
