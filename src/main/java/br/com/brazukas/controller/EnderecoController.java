package br.com.brazukas.controller;

import br.com.brazukas.DAO.EnderecoDAO;
import br.com.brazukas.Models.Endereco;
import br.com.brazukas.Models.EnderecoAlterar;
import br.com.brazukas.Models.Responses.ClienteResponse;
import br.com.brazukas.Models.Responses.EnderecoResponse;
import br.com.brazukas.Models.Responses.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @ApiOperation("Insere novo enderço para cliente")
    @RequestMapping(params = {"id"},method = RequestMethod.POST)
    public ResponseEntity<?> inserirEndereco(@RequestBody Endereco endereco, int id) throws IOException {

        EnderecoDAO.atualizaStatus(id,endereco.get_tipo());


        boolean inseriu = EnderecoDAO.inserirEndereco(endereco,id);

        if (inseriu) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200, "Endereço atualizado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para atualizar o Endereço!"));
        }
    }

    @ApiOperation("Atualiza enderço para cliente")
    @RequestMapping(params = {"id"},method = RequestMethod.PUT)
    public ResponseEntity<?> alterarEndereco(@RequestBody EnderecoAlterar endereco, int id) throws IOException {

        System.out.println("to aqui");

        boolean inseriu = EnderecoDAO.alterarEndereco(endereco,id);

        if (inseriu) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200, "Endereço atualizado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para atualizar o Endereço!"));
        }
    }

    @ApiOperation("Remove/inativa  enderço para cliente")
    @RequestMapping(params = {"id"},method = RequestMethod.DELETE)
    public ResponseEntity<?> mudarTipoEndereco(int id, String tipoEndereco) throws IOException {


        boolean inseriu = EnderecoDAO.inativarEndereco(id,tipoEndereco);

        if (inseriu) {
            return ResponseEntity.status(HttpStatus.OK).body(new ClienteResponse(200, "Endereço Inativado com sucesso!", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para atualizar o Endereço!"));
        }
    }

    @ApiOperation("Lista todos os endereços")
    @RequestMapping(params = {"id"},method = RequestMethod.GET)
    public ResponseEntity<?> getEnderecos(int id) throws IOException {
        List<EnderecoAlterar> enderecos = EnderecoDAO.listarEnderecosPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(new EnderecoResponse(200, "Endereço atualizado com sucesso!", enderecos));
    }

}
