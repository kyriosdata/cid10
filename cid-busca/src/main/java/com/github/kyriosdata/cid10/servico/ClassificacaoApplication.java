
/*
 *
 *  Copyright (c) 2018
 *
 *  Fábio Nogueira de Lucena
 *  Fábrica de Software - Instituto de Informática (UFG)
 *
 */

package com.github.kyriosdata.cid10.servico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Contêiner no qual o serviço de busca na CID-10 é realizado.
 * TODO https://spring.io/understanding/CORS
 * TODO https://spring.io/guides/gs/rest-service-cors/
 */
@SpringBootApplication
public class ClassificacaoApplication {

	public static void main(String[] args) {

		SpringApplication.run(ClassificacaoApplication.class, args);
	}
}
