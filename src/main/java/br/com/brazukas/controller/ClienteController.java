package br.com.brazukas.controller;

import br.com.brazukas.DAO.ClienteDAO;
import br.com.brazukas.Models.Cliente;
import br.com.brazukas.Models.ClienteAlterar;
import br.com.brazukas.Models.Responses.ClienteResponse;
import br.com.brazukas.Models.Responses.ErrorResponse;
import br.com.brazukas.Models.Responses.TokenResponse;
import br.com.brazukas.Util.ConverteSenhaParaMd5;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    @ApiOperation(value = "Atualiza um cliente")
    @RequestMapping( method = RequestMethod.PUT, value = "alterar")
    public ResponseEntity<?> Put(@RequestBody Cliente cliente, @RequestHeader("TOKEN") String meuToken) throws IOException, SQLException {
        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista cliente por ID", "/Cliente{Id}"));

        boolean alterou = ClienteDAO.alterarCliente(cliente);

        if (alterou) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200, "Cliente atualizado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para atualizar o cliente!"));
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

    @ApiOperation(value = "Busca um cliente")
    @RequestMapping(params = {"id"}, method = RequestMethod.GET , value = "/BuscarCliente")
    public ResponseEntity<?> filtraCliente(int id, @RequestHeader("TOKEN") String meuToken) throws IOException, SQLException {

        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista cliente por ID", "/Cliente{Id}"));

        Cliente cliente = ClienteDAO.getCliente(id);

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @ApiOperation(value = "Filtra cliente por email")
    @RequestMapping(params = {"email"}, method = RequestMethod.GET , value = "/BuscarCliente")
    public ResponseEntity<?> filtraClientePorEmail(String email) throws IOException, SQLException {

        Cliente cliente = ClienteDAO.getClientePorEmail(email);

        if(cliente == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "Nenhum cliente encontrado"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }


    @ApiOperation(value = "Atualiza um cliente")
    @RequestMapping( params = {"cpf"},method = RequestMethod.PUT, value = "atualiza")
    public ResponseEntity<?> atualizarCliente(@RequestBody ClienteAlterar cliente,String cpf /*,@RequestHeader("TOKEN") String meuToken*/) throws IOException, SQLException {
//        if (!TokenController.isValid(meuToken))
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Lista cliente por ID", "/Cliente{Id}"));


        cliente.set_password(ConverteSenhaParaMd5.convertToMd5(cliente.get_password()));

        cpf = cpf.replace(".","").replace("-","");

        System.out.println(cpf);

        var primeiroNome = cliente.get_nome().split(" ")[0];
        var segundoNome = cliente.get_nome().split(" ")[1];



        if (primeiroNome.length() < 3 || segundoNome.length() < 3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, "Nome Inválido!"));
        }

        boolean alterou = ClienteDAO.alterarClienteLoja(cliente,cpf);

        if (alterou) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200, "Cliente atualizado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para atualizar o cliente!"));
        }
    }

}
