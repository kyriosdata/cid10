package com.github.kyriosdata.bsus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Aplicacao {

	@RequestMapping(value = "/cid", params = { "q" })
	public String search(@RequestParam("q") String q) {
		return q + ";outra entrada";
	}

	public static void main(String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}
}
