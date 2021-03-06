package br.com.brazukas.controller;


import br.com.brazukas.DAO.VendaDAO;
import br.com.brazukas.Models.Responses.VendaResponse;
import br.com.brazukas.Models.Venda;
import br.com.brazukas.Models.VendaHasProduto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Vendas")
public class VendaController {
    @ApiOperation(value = "Retorna todas as vendas")
    @RequestMapping(method = RequestMethod.GET)
    public VendaResponse vendas() throws IOException {
        List<Venda> listaVenda = VendaDAO.consultarVenda();
        return  new VendaResponse(200,(int)listaVenda.size(),"Vendas encontradas", listaVenda);
    }

    @ApiOperation(value = "Retorna as vendas filtradas por ID")
    @RequestMapping(params = {"id"},method = RequestMethod.GET)
    public VendaResponse consultarPorId(int id) throws  IOException{
        Venda venda = VendaDAO.consultarVendaPorId(id);
        if(venda != null) {
            List<Venda> converterEmLista = new ArrayList<>();
            converterEmLista.add(venda);
            return  new VendaResponse(200,1,"Venda encontrada", converterEmLista);
        }
        return  new VendaResponse(404,0,"Nenhuma venda encontrada", null);
    }

    @ApiOperation(value = "Inserir uma nova venda")
    @RequestMapping(method = RequestMethod.POST)
    public VendaResponse inserirVenda(@RequestBody VendaHasProduto venda){
        boolean inseriu = VendaDAO.inserirVenda(venda);

        if(inseriu){
            return  new VendaResponse(200,1,"Venda inserida com sucesso", null);
        }else{
            return  new VendaResponse(500,0,"Erro para inserir venda", null);
        }

    }
}
