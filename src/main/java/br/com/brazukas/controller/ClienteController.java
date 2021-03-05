package br.com.brazukas.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.Models.Cliente;

@CrossOrigin
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	
	@RequestMapping(params={"cpf"}, method = RequestMethod.PUT)
	public String Put(@RequestBody Cliente cliente, String cpf) throws IOException, SQLException{
		boolean alterou = ClienteDAO.alterarCliente(cliente);
				
		if(alterou) {
			return "Cliente alterado com sucesso!";
		}else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao alterar as informações do cliente!");
		}
	}

	@RequestMapping(params={"cpf"}, method = RequestMethod.DELETE)
	public String Deletar ( String cpf) throws IOException, SQLException{
		boolean deletou = ClienteDAO.excluirCliente(cpf);
		
		if(deletou) {
			return "Cliente deletado com sucesso!";
		}else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir o cliente!");
		}
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
public String Post(@RequestBody Cliente cliente) throws IOException, SQLException{
	
	boolean inseriu = ClienteDAO.inserirCliente(cliente);
	
	if (inseriu) {
		return "Cliente cadastrado com sucesso!";
	}else {
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar o cliente!");
	}
}
	
	
@RequestMapping(method = RequestMethod.GET)
public List<Cliente> ListaClientes() throws IOException, SQLException{
	
	List<Cliente> listaCliente = ClienteDAO.consultarCliente();
	
	return listaCliente;
}
	

@RequestMapping(params = {"cpf"}, method = RequestMethod.GET)
	public Cliente filtraCliente(String cpf) throws IOException, SQLException{
	
	Cliente lista = ClienteDAO.getCliente(cpf);
	System.out.println(lista);
	return lista;
}

}
