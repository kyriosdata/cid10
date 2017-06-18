package com.github.kyriosdata.bsus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Aplicacao {

	@RequestMapping(value = "/cid", method = RequestMethod.POST)
	public String search(@RequestParam String q) {
		return "RECEBIDO: " + q;
	}

	public static void main(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}
}
