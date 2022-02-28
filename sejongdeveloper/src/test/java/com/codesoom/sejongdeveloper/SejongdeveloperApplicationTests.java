package com.codesoom.sejongdeveloper;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class SejongdeveloperApplicationTests {

	@MockBean
	private UserService userService;

	@MockBean
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
	}

}
