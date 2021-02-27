package br.com.brazukas.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import br.com.brazukas.Models.*;

@Controller
public class IndexController {

	@RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Index hello() {
		return new Index("Mensagem", "Teste");
	}
}
