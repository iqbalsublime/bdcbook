package com.bdcyclists.bdcbook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bdcyclists.bdcbook.BdcbookApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BdcbookApplication.class)
@WebAppConfiguration
public class HomeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppContext;

	@Before
	public void setUp() throws Exception {
		// Setup Spring test in webapp-mode (same config as spring-boot)
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.build();
	}

	@Test
	public void showHome_ShouldRenderHomeView() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(view().name(HomeController.VIEW_NAME_HOME));

	}

}
