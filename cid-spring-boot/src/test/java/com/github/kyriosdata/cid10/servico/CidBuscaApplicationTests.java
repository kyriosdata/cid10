package com.github.kyriosdata.cid10.servico;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CidBuscaApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void recuperaEntradaUnica() throws Exception {
		String url = "/busca/a9 w/0";
		MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get(url);
		mock.accept(MediaType.APPLICATION_JSON);
		ResultActions resultado = mvc.perform(mock);
		resultado.andExpect(status().isOk());
		resultado.andExpect(content().string(containsString("A923")));
		resultado.andExpect(content().json("[\"A923;-;Infecção pelo vírus West Nile\"]"));
	}

	@Test
	public void ordemAlemDoTamanhoDaRespostaNadaProduzido() throws Exception {
		String url = "/busca/a9 w/1";
		MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get(url);
		mock.accept(MediaType.APPLICATION_JSON);
		ResultActions resultado = mvc.perform(mock);
		resultado.andExpect(status().isOk());
		resultado.andExpect(content().string(equalTo("[]")));
	}

}
