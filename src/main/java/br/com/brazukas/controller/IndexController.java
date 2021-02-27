package br.com.brazukas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello World";
	}
	
}
