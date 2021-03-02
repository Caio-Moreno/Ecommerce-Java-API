package br.com.brazukas.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.Models.Cliente;

@CrossOrigin
@RestController
@RequestMapping("/Clientes")
public class ClienteController {
	
@RequestMapping(method = RequestMethod.GET)

public List<Cliente> ListaClientes() throws IOException, SQLException{
	
	List<Cliente> listaCliente = ClienteDAO.consultarCliente();
	
	return listaCliente;
}
	

@RequestMapping(params = {"cpf"})

	public List<Cliente> filtraCliente(String cpf) throws IOException, SQLException{
	
	List<Cliente> lista = ClienteDAO.getCliente(cpf);
	return lista;
}

}
