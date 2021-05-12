package br.com.brazukas.controller;


import br.com.brazukas.DAO.VendaDAO;
import br.com.brazukas.Models.Payment;
import br.com.brazukas.Models.Responses.ErrorResponse;
import br.com.brazukas.Models.Responses.VendaResponse;
import br.com.brazukas.Models.Venda;
import br.com.brazukas.Models.VendaHasProduto;
import br.com.brazukas.Util.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Vendas")
public class VendaController {

    @ApiOperation(value = "Inserir uma nova venda")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> inserirVenda(@RequestBody VendaHasProduto venda){
        try {
            int id = VendaDAO.inserirVenda(venda);

            if (id > 0) {
                return ResponseEntity.ok(new VendaResponse(200, "Venda inserida com sucesso", id));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro para inserir pagamento"));
            }
        }catch (Exception e){
            Utils.printarErro("erro"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Error"+e.getMessage()));
        }
    }

    @ApiOperation(value = "Insere forma de pagamento venda")
    @RequestMapping(method = RequestMethod.POST, value = "/paymentoInsert")
    public ResponseEntity<?> inserePagamento(@RequestBody Payment payment){
        try {
            boolean inseriu = VendaDAO.insertPayment(payment);

            if (inseriu) {
                return ResponseEntity.ok(new VendaResponse(200, "pagamento inserido", payment.get_idVendaFk()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new VendaResponse(500,"Erro para inserir pagamento", 0));
            }
        }catch (Exception e){
            Utils.printarErro("erro"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Error"+e.getMessage()));
        }

    }

}
