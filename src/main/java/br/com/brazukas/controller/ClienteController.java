package br.com.brazukas.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.brazukas.Models.Responses.ClienteResponse;
import br.com.brazukas.Models.Responses.ErrorResponse;
import br.com.brazukas.Models.Responses.TokenResponse;
import br.com.brazukas.Util.ConverteSenhaParaMd5;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.Models.Cliente;

@CrossOrigin
@RestController
@RequestMapping("/Clientes")
public class ClienteController {

    @ApiOperation(value = "Insere um cliente")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> Post(@RequestBody Cliente cliente) throws IOException, SQLException {

        cliente.set_cpf(cliente.get_cpf().replace(".","").replace("-",""));
        System.out.println("Meu cliente"+cliente);
        //verifico se existe um email já cadastrado
        if (ClienteDAO.ExisteEmail(cliente.get_email()))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Email já cadastrado"));
        //verifico se existe um cpf já cadastrado
        if (ClienteDAO.ExisteCpf(cliente.get_cpf()))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "CPF já cadastrado"));

        cliente.set_senha(ConverteSenhaParaMd5.convertToMd5(cliente.get_senha()));

        boolean inseriu = ClienteDAO.inserirCliente(cliente);

        if (inseriu) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200,"Cliente inserido com sucesso", null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(500,"Erro para inserir o cliente", null));
        }
    }


    @RequestMapping(params = {"cpf"}, method = RequestMethod.PUT)
    public String Put(@RequestBody Cliente cliente, String cpf) throws IOException, SQLException {
        boolean alterou = ClienteDAO.alterarCliente(cliente);

        if (alterou) {
            return "Cliente alterado com sucesso!";
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao alterar as informações do cliente!");
        }
    }

    @RequestMapping(params = {"cpf"}, method = RequestMethod.DELETE)
    public String Deletar(String cpf) throws IOException, SQLException {
        boolean deletou = ClienteDAO.excluirCliente(cpf);

        if (deletou) {
            return "Cliente deletado com sucesso!";
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao excluir o cliente!");
        }

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Cliente> ListaClientes() throws IOException, SQLException {

        List<Cliente> listaCliente = ClienteDAO.consultarCliente();

        return listaCliente;
    }


    @RequestMapping(params = {"cpf"}, method = RequestMethod.GET)
    public Cliente filtraCliente(String cpf) throws IOException, SQLException {
        Cliente lista = ClienteDAO.getCliente(cpf);
        System.out.println(lista);
        return lista;
    }

}
