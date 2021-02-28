package br.com.brazukas.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import br.com.brazukas.Models.*;
import br.com.brazukas.Util.ConexaoDb;

import java.io.IOException;

@Controller
public class IndexController {

	@RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Index hello() throws IOException {

		ConexaoDb conexao = new ConexaoDb();
		ConexaoDb.getConnection();


		return new Index("Mensagem", "Teste");
	}
}
