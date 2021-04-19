package br.com.brazukas.controller;


import br.com.brazukas.DAO.DashboardDAO;
import br.com.brazukas.DAO.ProdutoDAO;
import br.com.brazukas.Models.ProdutoPlataforma;
import br.com.brazukas.Models.ProdutoStatus;
import br.com.brazukas.Models.Responses.*;
import br.com.brazukas.Models.UsuarioCargo;
import br.com.brazukas.Models.UsuarioStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/Dashboard")
public class DashboardController {
    @ApiOperation(value = "Get Status e plataforma Produto")
    @RequestMapping(params = {"tipo"}, method = RequestMethod.GET)
    public ResponseEntity<?> consultas(String tipo, @RequestHeader("TOKEN") String meuToken) throws IOException {
        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Atualiza status do produto", "/Produtos"));


        if(tipo.equals("status")) {
            ProdutoStatus prod = DashboardDAO.consultaStatus();
            return ResponseEntity.status(HttpStatus.OK).body(new ProdutoStatusResponse(200, "Status encontrado", prod));
        }else {
            ProdutoPlataforma prod = DashboardDAO.consultaPlataforma();
            return ResponseEntity.status(HttpStatus.OK).body(new ProdutoPlataformaResponse(200, "Plataforma encontrada", prod));
        }
    }

    @ApiOperation(value = "Get Status e Tipo Usuario")
    @RequestMapping(value = "/listaUsuario",params = "tipo", method = RequestMethod.GET)
    public ResponseEntity<?> consultaCliente(String tipo, @RequestHeader("TOKEN") String meuToken){
        System.out.println(meuToken);
        if (!TokenController.isValid(meuToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse(401, "Token inválido", "Atualiza status do produto", "/Produtos"));

        if(tipo.equals("status")){
            UsuarioStatus status = DashboardDAO.consultaStatusUsuario();
            if(status == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioStatusResponse(404, "Nenhuma informação encontada", null));

            return ResponseEntity.status(HttpStatus.OK).body(new UsuarioStatusResponse(200, "Dados encontrados", status));
        }else{
            UsuarioCargo cargo = DashboardDAO.consultaPorCargo();
            if(cargo == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioStatusResponse(404, "Nenhuma informação encontada", null));
            return ResponseEntity.status(HttpStatus.OK).body(new UsuarioCargoResponse(200, "Dados encontrados", cargo));
        }

    }
}

