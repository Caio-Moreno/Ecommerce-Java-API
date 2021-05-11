package br.com.brazukas.DAO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import br.com.brazukas.Models.Cliente;
import br.com.brazukas.Models.ClienteAlterar;
import br.com.brazukas.Models.Endereco;
import br.com.brazukas.Models.EnderecoAlterar;
import br.com.brazukas.Util.ConexaoDb;
import br.com.brazukas.Util.ConverteSenhaParaMd5;
import br.com.brazukas.Util.Utils;

import static br.com.brazukas.Util.CriarArquivoDeLog.gravaLog;

public class ClienteDAO {
	
	public static boolean alterarCliente (Cliente cliente) throws IOException, SQLException{
		String sqlAlterar = retornaQuery(cliente);
		
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
		String sqlInserirE = "INSERT INTO CLIENTE_ENDERECO(ID,ID_CLIENTE_FK, CEP, LOGRADOURO, BAIRRO, NUMERO, COMPLEMENTO, CIDADE,ESTADO,TIPO)\n" +
				"VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
			for (EnderecoAlterar end : cliente.get_endereco()) {
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
	
	public static Cliente getCliente(int id) throws IOException {
		Utils.printarNaTela("Entrei");
		Cliente cliente = null;
		List<EnderecoAlterar> listaEndereco = new ArrayList<>();
		String sqlConsulta = "SELECT a.ID, a.NOME, a.CPF, a.SEXO, a.DATANASCIMENTO, a.EMAIL\n" +
				", c.TELEFONE\n" +
				"FROM USUARIO a\n" +
				"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
				"WHERE 1=1 \n" +
				"AND a.ID = ?;";
		Utils.printarNaTela("Entrei");
		int idCliente =0;
		String nome = "";
		String cpf = "";
		String sexo = "";
		String datanascimento = "";
		String email = "";
		String telefone = "";





		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);

			Utils.printarNaTela("Entrei");

			ps.setInt(1, id);
			Utils.printarMinhaConsulta(ps);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				 idCliente = rs.getInt("ID");
				 nome = rs.getString("NOME");
				 cpf = rs.getString("CPF");
				 sexo = rs.getString("SEXO");
				 datanascimento = rs.getString("DATANASCIMENTO");
				 email = rs.getString("EMAIL");
				 telefone = rs.getString("TELEFONE");


			}
			listaEndereco = EnderecoDAO.listarEnderecosPorId(idCliente);
			cliente = new Cliente (idCliente,nome,email,cpf,datanascimento,"",sexo,telefone,listaEndereco);
		
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Erro para buscar dados do cliente");
		}

		return cliente;
	}

	public static boolean clienteExite(int id) throws IOException {

		Utils.printarNaTela("Consulta cliente por id" + id);
		String sqlConsulta = "SELECT * FROM USUARIO WHERE ID = ? ; ";

		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps);
			return rs.next();
		} catch (SQLException | IOException e) {
			Utils.printarErro("Erro de SQL Exception-->" + e.getMessage());
			return false;
		}
	}

	public static boolean existeSessao(String sessionId) throws IOException {
		Utils.printarNaTela("EXISTE SESSAO"+sessionId);
		String sqlConsulta = "SELECT * FROM CARRINHO WHERE TOKEN_SESSION = ? ; ";
		Utils.printarNaTela(sqlConsulta);

		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);
			ps.setString(1, sessionId);
			Utils.printarMinhaConsulta(ps);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			Utils.printarErro( e.getMessage());
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

	public static String retornaQuery(Cliente cliente){
		var endereco = cliente.get_endereco();
		String query = "";

			if(cliente.get_endereco() == null) {
				if(cliente.get_senha() == null){
					query = "UPDATE USUARIO a\n" +
							"INNER JOIN CLIENTE_ENDERECO b ON a.ID = b.ID_CLIENTE_FK\n" +
							"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
							"set a.NOME = ?, a.SEXO = ?, c.TELEFONE = ?";
				}else{
					query = "UPDATE USUARIO a\n" +
							"INNER JOIN CLIENTE_ENDERECO b ON a.ID = b.ID_CLIENTE_FK\n" +
							"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
							"set a.NOME = ?, a.PASSWORD = ?, a.SEXO = ?, c.TELEFONE = ?";
				}
			}else{
				if(endereco.size() == 2){
					int cont = 0;
					for (var end : endereco){
						if(cont == 0) {
							query += "UPDATE USUARIO a\n" +
									"INNER JOIN CLIENTE_ENDERECO b ON a.ID = b.ID_CLIENTE_FK\n" +
									"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
									"set a.NOME = ?, a.PASSWORD = ?, a.SEXO = ?, c.TELEFONE = ?\n" +
									",b.CEP = ?,b.LOGRADOURO = ?, b.NUMERO = ?, b.COMPLEMENTO = ?, b.BAIRRO = ?, b.ESTADO = ?, b.CIDADE= ?, b.TIPO = ?|";
							cont++;
						}else{
							query += "UPDATE USUARIO a\n" +
									"INNER JOIN CLIENTE_ENDERECO b ON a.ID = b.ID_CLIENTE_FK\n" +
									"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
									"set a.NOME = ?, a.PASSWORD = ?, a.SEXO = ?, c.TELEFONE = ?\n" +
									",b.CEP = ?,b.LOGRADOURO = ?, b.NUMERO = ?, b.COMPLEMENTO = ?, b.BAIRRO = ?, b.ESTADO = ?, b.CIDADE= ?, b.TIPO = ?";
						}
					}
				}else{

				}
			}

			Utils.printarNaTela("Retorno da query"+query);
			return query;
	}


    public static Cliente getClientePorEmail(String emailCliente) {
		Utils.printarNaTela("Entrei");
		boolean encontrei = false;
		Cliente cliente = null;
		List<EnderecoAlterar> listaEndereco = new ArrayList<>();
		String sqlConsulta = "SELECT a.ID, a.NOME, a.CPF, a.SEXO, a.DATANASCIMENTO, a.EMAIL\n" +
				",b.CEP,b.ID,b.LOGRADOURO, b.BAIRRO, b.NUMERO,b.COMPLEMENTO, b.CIDADE, b.ESTADO, b.TIPO\n" +
				", c.TELEFONE\n" +
				"FROM USUARIO a\n" +
				"INNER JOIN CLIENTE_ENDERECO b ON a.ID = b.ID_CLIENTE_FK\n" +
				"INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK\n" +
				"WHERE 1=1 \n" +
				"AND a.EMAIL = ?\n" +
				"ORDER BY b.TIPO asc;";
		Utils.printarNaTela("Entrei");
		int idCliente =0;
		int idEndereco =0;
		String nome = "";
		String cpf = "";
		String sexo = "";
		String datanascimento = "";
		String email = "";
		String cep = "";
		String endereco = "";
		String bairro = "";
		int numero = 0;
		String complemento = "";
		String cidade = "";
		String estado = "";
		String tipo = "";
		String telefone = "";





		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlConsulta);

			Utils.printarNaTela("Entrei");

			ps.setString(1, emailCliente);
			Utils.printarMinhaConsulta(ps);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				encontrei = true;
				idCliente = rs.getInt("ID");
				nome = rs.getString("NOME");
				cpf = rs.getString("CPF");
				sexo = rs.getString("SEXO");
				datanascimento = rs.getString("DATANASCIMENTO");
				email = rs.getString("EMAIL");
				idEndereco = rs.getInt("ID");
				cep = rs.getString("CEP");
				endereco = rs.getString("LOGRADOURO");
				bairro = rs.getString("BAIRRO");
				numero = rs.getInt("NUMERO");
				complemento = rs.getString("COMPLEMENTO");
				cidade = rs.getString("CIDADE");
				estado = rs.getString("ESTADO");
				tipo = rs.getString("TIPO");
				telefone = rs.getString("TELEFONE");

				listaEndereco.add(new EnderecoAlterar(cep,endereco,numero,complemento,bairro,cidade,estado,tipo,idEndereco));
			}
			if(encontrei) {
				cliente = new Cliente(idCliente, nome, email, cpf, datanascimento, "", sexo, telefone, listaEndereco);
			}else{
				cliente = null;
			}


		} catch (SQLException | IOException e) {
			cliente = null;
			System.out.println("Erro para buscar dados do cliente");
		}

		return cliente;
    }

    public static boolean alterarSenha(String email, String senha) throws UnsupportedEncodingException {
		senha = ConverteSenhaParaMd5.convertToMd5(senha);
		String sqlUpdate = "UPDATE USUARIO \n" +
				"SET PASSWORD = ?\n" +
				"WHERE EMAIL = ?;";
		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlUpdate);
			ps.setString(1, senha);
			ps.setString(2, email);
			ps.execute();
		}catch (Exception e){
			Utils.printarErro(e.getMessage());
			return  false;
		}

		return true;
	}

	public static boolean alterarClienteLoja(ClienteAlterar cliente, String cpf) throws IOException {
		String sqlAlterar = "UPDATE USUARIO a" +
				" INNER JOIN CLIENTE_TELEFONE c ON a.ID = c.ID_CLIENTE_FK " +
				"set a.NOME = ?, a.PASSWORD = ?, a.SEXO = ?, c.TELEFONE = ? WHERE a.CPF = ?;"
				;


		try {
			Connection con = ConexaoDb.getConnection();
			PreparedStatement ps = con.prepareStatement(sqlAlterar);


			ps.setString(1, cliente.get_nome());
			ps.setString(2, cliente.get_password());
			ps.setString(3, cliente.get_sexo());
			ps.setString(4, cliente.get_telefone());
			ps.setString(5,cpf);

			ps.execute();

			return true;

		}catch (SQLException | IOException e) {
			gravaLog("Erro de SQL Exception-->" + e.getMessage(), "ClienteDAO", Level.WARNING);
			return false;
		}
	}


}
