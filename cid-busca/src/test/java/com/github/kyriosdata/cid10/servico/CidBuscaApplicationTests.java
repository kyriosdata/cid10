package com.github.kyriosdata.cid10.servico;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CidBuscaApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void recuperaEntradaUnica() throws Exception {
		String url = "/busca/a9 w/6";
		MockHttpServletRequestBuilder mock = MockMvcRequestBuilders.get(url);
		mock.accept(MediaType.APPLICATION_JSON);
		ResultActions resultado = mvc.perform(mock);
		resultado.andExpect(status().isOk());
		resultado.andExpect(content().string(containsString("A923")));
		resultado.andExpect(content().json("[\"A923;-;Infecção pelo vírus West Nile\"]"));
	}

}
