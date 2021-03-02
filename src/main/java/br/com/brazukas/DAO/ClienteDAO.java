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
            	int idCliente = rs.getInt("ID_CLIENTE");
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
	
	public static List<Cliente> getCliente(String cpf) throws IOException {
		List<Cliente> filtroCliente = new ArrayList<>();

		gravaLog("Consulta cliente por cpf" + cpf, "ClienteDAO", Level.INFO);

		String sqlConsulta = "SELECT * FROM CLIENTE WHERE CPF = ? ; ";

		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);

			ps.setString(1, cpf);

			ResultSet rs = ps.executeQuery();

			System.out.println(ps);

			while (rs.next()) {
				int idCliente = rs.getInt("IDCLIENTE");
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
				
				filtroCliente.add(new Cliente(idCliente, nome, cpf, sexo, dataNascimento, telefone, email, cep, endereco, bairro, numero, complemento, cidade, estado));

			}

		} catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
		}

		return filtroCliente;
	}

}
